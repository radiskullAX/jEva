package eu.animegame.jeva.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.exceptions.ConnectException;
import eu.animegame.jeva.core.lifecycle.LifecycleState;
import eu.animegame.jeva.core.lifecycle.Startup;

/**
 *
 * @author radiskull
 */
public class IrcHandler {

  public final static String PROP_NICK = "jeva.irc.nick";

  public final static String PROP_PASSWORD = "jeva.irc.password";

  public final static String PROP_REAL_NAME = "jeva.irc.realName";

  public final static String PROP_SERVER = "jeva.irc.server";

  public final static String PROP_PORT = "jeva.irc.port";

  public final static String PROP_MODE = "jeva.irc.mode";

  private final static Logger LOG = LoggerFactory.getLogger(IrcHandler.class);

  private final List<IrcHandlerPlugin> plugins;

  private final Properties config;

  private final Connection connection;

  private Map<String, List<CallbackEntry>> callbacks = new HashMap<>();

  private LifecycleState state;

  private boolean stopped = false;

  private boolean started = false;

  public IrcHandler(Connection connection) {
    this(new Properties(), connection);
  }

  public IrcHandler(Properties config, Connection connection) {
    this.config = config;
    this.connection = connection;
    this.state = new Startup();
    this.plugins = new ArrayList<>();
  }

  public Properties getConfiguration() {
    return config;
  }

  public void setState(LifecycleState state) {
    LOG.debug("State going to change from '{}' to '{}'", this.state.getClass().getSimpleName(),
        state.getClass().getSimpleName());
    this.state = state;
  }

  public String getState() {
    return state.getClass().getSimpleName();
  }

  public void start() {
    try {
      started = true;
      do {
        state.run(this);
      } while (!isStopped());

      // reset lifecycle in case we stopped unexpectedly
      setState(new Startup());
      started = false;
      stopped = false;
    } finally {
      try {
        connection.disconnect();
      } catch (Exception e) {
        LOG.warn("Failed to close connection", e);
      }
    }
  }

  private boolean isStopped() {
    return stopped || state.getClass().equals(Startup.class) || Thread.interrupted();
  }

  public void stop() {
    this.stopped = true;
  }

  public boolean isRunning() {
    return started;
  }

  public void addPlugin(IrcHandlerPlugin plugin) {
    if (!plugins.contains(plugin)) {
      plugins.add(plugin);
    }
  }

  public boolean removePlugin(IrcHandlerPlugin plugin) {
    return plugins.remove(plugin);
  }

  public List<IrcHandlerPlugin> getPlugins() {
    return Collections.unmodifiableList(plugins);
  }

  public void fireLifecycleState(Consumer<IrcHandlerPlugin> consumer) {
    plugins.forEach(consumer);
  }

  public void lookup() {
    callbacks = plugins.stream()
        .map(this::toListOfCallbacks)
        .flatMap(map -> map.entrySet().stream())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue, this::mergeLists));

    LOG.info("Found {} methods for {} irc commands", callbacks.values().stream().mapToInt(l -> l.size()).sum(),
        callbacks.size());
    LOG.debug("Commands are: {}", callbacks.keySet().stream().collect(Collectors.joining(",")));
  }

  private List<CallbackEntry> mergeLists(List<CallbackEntry> leftList, List<CallbackEntry> rightList) {
    leftList.addAll(rightList);
    return leftList;
  }

  private Map<String, List<CallbackEntry>> toListOfCallbacks(IrcHandlerPlugin plugin) {
    return Stream.of(plugin.getClass().getMethods())
        .filter(m -> Objects.nonNull(m.getAnnotation(IrcEventAcceptor.class)))
        .map(m -> new CallbackEntry(plugin, m))
        .collect(
            Collectors.groupingBy(cbe -> cbe.method.getAnnotation(IrcEventAcceptor.class).command().toUpperCase()));
  }

  public void connect() throws ConnectException, Exception {
    connection.connect();
  }

  public void disconnect() throws Exception {
    connection.disconnect();
  }

  public String readCommand() throws ConnectException, Exception {
    return connection.read();
  }

  public void sendCommand(IrcCommand command) {
    if (command != null) {
      try {
      connection.write(command.build());
      } catch (Exception e) {
        LOG.warn("Could not send message to server", e);
      }
    }
  }

  public void fireIrcEvent(final IrcBaseEvent event) {
    List<CallbackEntry> entries = callbacks.get(event.getCommand());
    if (entries != null) {
      entries.stream().forEach(m -> invokeMethod(m, event));
    }
  }

  private void invokeMethod(CallbackEntry entry, IrcBaseEvent event) {
    try {
      entry.method.invoke(entry.plugin, event);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      LOG.warn("Could not invoke method '{}' for event '{}'", entry.method.getName(), event.getClass().getSimpleName(),
          e);
    }
  }

  private class CallbackEntry {

    private final IrcHandlerPlugin plugin;

    private final Method method;

    public CallbackEntry(IrcHandlerPlugin plugin, Method method) {
      this.plugin = plugin;
      this.method = method;
    }
  }
}

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
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.exceptions.ConnectException;
import eu.animegame.jeva.core.lifecycle.Initialize;
import eu.animegame.jeva.core.lifecycle.LifecycleState;

/**
 *
 * @author radiskull
 */
public class IrcHandler {

  private static final Logger LOG = LoggerFactory.getLogger(IrcHandler.class);

  private final List<IrcHandlerPlugin> plugins;

  private final IrcConfig config;

  private final Connection connection;

  private Map<String, List<CallbackEntry>> callbacks = new HashMap<>();

  private LifecycleState state;

  private boolean stopped = false;

  private boolean started = false;

  // TODO: parse command args
  public IrcHandler(String... args) {
    this(new SocketConnection(), args);
  }

  public IrcHandler(Connection connection, String... args) {
    this.config = new IrcConfig();
    this.connection = connection;
    this.state = new Initialize();
    this.plugins = new ArrayList<>();
  }

  public IrcConfig getConfig() {
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
      setState(new Initialize());
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
    return stopped || state.getClass().equals(Initialize.class) || Thread.interrupted();
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

  // TODO: maybe do this when a plugin gets registered, this will be needed for plug & play
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

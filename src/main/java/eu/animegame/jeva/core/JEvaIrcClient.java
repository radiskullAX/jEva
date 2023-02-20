package eu.animegame.jeva.core;

import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.exceptions.ConnectException;
import eu.animegame.jeva.core.lifecycle.Initialize;
import eu.animegame.jeva.core.lifecycle.LifecycleState;
import eu.animegame.jeva.util.ForTestsOnly;

/**
 *
 * @author radiskull
 */
public class JEvaIrcClient {

  private static final Logger LOG = LoggerFactory.getLogger(JEvaIrcClient.class);

  protected final IrcPluginController pluginController;

  protected final IrcConfig config;

  protected final Connection connection;

  protected LifecycleState state;

  private boolean stopped = false;

  private boolean started = false;

  // TODO: parse command args
  public JEvaIrcClient(String... args) {
    this(new SocketConnection(), args);
  }

  public JEvaIrcClient(Connection connection, String... args) {
    this.connection = connection;
    this.config = new IrcConfig();
    this.state = new Initialize();
    this.pluginController = new IrcPluginController(this);
  }

  @ForTestsOnly
  JEvaIrcClient(Connection connection, IrcPluginController pluginController, String... args) {
    this.connection = connection;
    this.pluginController = pluginController;
    this.config = new IrcConfig();
    this.state = new Initialize();
  }

  @ForTestsOnly
  Connection getConnection() {
    return connection;
  }

  @ForTestsOnly
  IrcPluginController getPluginController() {
    return pluginController;
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
    // TODO: set lifecycle start here and other things that need to be set
    // TODO: make it that start can't be run twice
    connection.setConfig(config);
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

  // TODO: Think about thrown Exceptions once more here
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

  private boolean isStopped() {
    return stopped || state.getClass().equals(Initialize.class) || Thread.interrupted();
  }

  public void stop() {
    this.stopped = true;
  }

  public boolean isRunning() {
    return started;
  }

  public void addPlugin(JEvaIrcPlugin plugin) {
    pluginController.addPlugin(plugin);
  }

  public boolean removePlugin(JEvaIrcPlugin plugin) {
    return pluginController.removePlugin(plugin);
  }

  public List<JEvaIrcPlugin> getPlugins() {
    return pluginController.getPlugins();
  }

  public void lookup() {
    pluginController.lookup();
  }

  public void fireLifecycleState(Consumer<JEvaIrcPlugin> consumer) {
    pluginController.fireLifecycleState(consumer);
  }


  public void fireIrcEvent(final IrcBaseEvent event) {
    pluginController.fireIrcEvent(event);
  }
}

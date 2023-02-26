package eu.animegame.jeva.core;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.exceptions.JEvaException;
import eu.animegame.jeva.core.exceptions.LifeCycleException;
import eu.animegame.jeva.core.exceptions.UnknownFormatException;
import eu.animegame.jeva.core.lifecycle.Initialize;
import eu.animegame.jeva.core.lifecycle.LifeCycleObject;
import eu.animegame.jeva.util.ForTestsOnly;

/**
 *
 * @author radiskull
 */
public class JEvaIrcClient {

  private static final Logger LOG = LoggerFactory.getLogger(JEvaIrcClient.class);

  private final IrcPluginController pluginController;

  private final IrcConfig config;

  private final Connection connection;

  private final LifeCycle lifeCycleStateMachine;

  private final IrcCommandParser parser;

  private boolean stopped = false;

  private boolean started = false;

  private boolean running = false;

  // TODO: parse command args
  // TODO: args override config values
  public JEvaIrcClient(String... args) {
    this(new SocketConnection(), args);
  }

  public JEvaIrcClient(Connection connection, String... args) {
    this(connection, new IrcConfig(), args);
  }

  public JEvaIrcClient(Connection connection, IrcConfig config, String... args) {
    this.connection = connection;
    this.config = config;
    this.parser = new IrcCommandParser();
    this.pluginController = new IrcPluginController(this);
    this.lifeCycleStateMachine = new LifeCycle(new Initialize(new LifeCycleListener(this)));
  }

  @ForTestsOnly
  JEvaIrcClient(Connection connection, IrcPluginController pluginController, String... args) {
    this.connection = connection;
    this.pluginController = pluginController;
    this.config = new IrcConfig();
    this.parser = new IrcCommandParser();
    this.lifeCycleStateMachine = new LifeCycle(new Initialize(new LifeCycleListener(this)));
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

  public void start() {
    if (!started) {
      started = true;
      stopped = false;
      connection.setConfig(config);
      lifeCycleStateMachine.start();
    }
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

  public void stop() {
    this.stopped = true;
  }

  public boolean isRunning() {
    return running;
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

  public <P> Optional<P> getPlugin(Class<P> pluginClass) {
    return pluginController.getPlugin(pluginClass);
  }

  public static JEvaIrcClientBuilder builder() {
    return JEvaIrcClientBuilder.create();
  }

  private class LifeCycleListener implements LifeCycleObject {

    private JEvaIrcClient engine;

    public LifeCycleListener(JEvaIrcClient engine) {
      this.engine = engine;
    }

    @Override
    public void initialize() throws LifeCycleException {
      LOG.info("engine state: [state: initialize]");
      pluginController.lookup();
      pluginController.fireLifecycleState(p -> p.initialize(engine));
    }

    @Override
    public void connect() throws LifeCycleException {
      LOG.info("engine state: [state: connect]");
      try {
        connection.connect();
        pluginController.fireLifecycleState(p -> p.connect(engine));
        running = true;
      } catch (Exception e) {
        throw new LifeCycleException(e);
      }
    }

    @Override
    public void read() throws LifeCycleException {
      LOG.info("engine state: [state: read]");
      try {
        while (!isStopped()) {
          var message = connection.read();
          var event = parser.toIrcEvent(message);
          pluginController.fireIrcEvent(event);
        }
      } catch (UnknownFormatException e) {
        // TODO: not sure if the lifecycle should die here or not .. have to think about it
        LOG.warn("failed to parse message: [message: '{}']", e.getUnknownMessage(), e);
      } catch (JEvaException e) {
        throw new LifeCycleException(e);
      } catch (Exception e) {
        throw e;
      }
    }

    private boolean isStopped() {
      return stopped || Thread.interrupted();
    }

    @Override
    public void disconnect() throws LifeCycleException {
      LOG.info("engine state: [state: disconnect]");
      try {
        pluginController.fireLifecycleState(p -> p.disconnect(engine));
      } catch (Exception e) {
        throw new LifeCycleException();
      } finally {
        try {
        connection.disconnect();
        } catch (Exception e) {
          LOG.warn("failed to close connection", e);
        }
      }
    }

    @Override
    public void shutdown() throws LifeCycleException {
      LOG.info("engine state: [state: shutdown]");
      try {
        pluginController.fireLifecycleState(p -> p.shutdown(engine));
      } catch (Exception e) {
        LOG.warn("failed to shut down plugins", e);
      }
      started = false;
      running = false;
    }
  }
}

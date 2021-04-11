package eu.animegame.jeva.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.lifecycle.LifecycleState;
import eu.animegame.jeva.core.lifecycle.Startup;

/**
 *
 * @author radiskull
 */
public class IrcHandler {

  public final static String PROP_NICK = "jeva.irc.nick";

  public final static String PROP_REAL_NAME = "jeva.irc.realName";

  public final static String PROP_SERVER = "jeva.irc.server";

  public final static String PROP_PORT = "jeva.irc.port";

  public final static String PROP_MODE = "jeva.irc.mode";

  private final static Logger LOG = LoggerFactory.getLogger(IrcHandler.class);

  private final List<IrcHandlerPlugin> plugins;

  private final Properties config;

  private LifecycleState state;

  public IrcHandler(Properties config) {
    this.config = config;
    this.state = new Startup();
    this.plugins = new ArrayList<>();
  }

  public Properties getConfiguration() {
    return config;
  }

  public void setState(LifecycleState state) {
    var oldState = this.state;
    this.state = state;
    LOG.debug("State going to change from '{}' to '{}'", oldState.getClass().getSimpleName(),
        state.getClass().getSimpleName());
  }

  public void start() {
    do {
      state.run(this);
    } while (!state.getClass().equals(Startup.class));
  }

  public void stop() {
    // TODO: implement
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
    return plugins;
  }

  public void lookup() {
    // TODO: implement
  }

  public void createConnection() throws IOException {
    // TODO: implement
  }

  public String readCommand() {
    // TODO: implement
    return null;
  }

  public void fireIrcEvent(IrcBaseEvent event) {

  }

  public void sendCommand(IrcCommand command) {
    if (command != null) {
      // connection.write(command.build());
    }
  }

}

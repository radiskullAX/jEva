package eu.animegame.jeva.plugins;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.core.lifecycle.Initialize;
import eu.animegame.jeva.irc.commands.Nick;
import eu.animegame.jeva.irc.commands.Pass;
import eu.animegame.jeva.irc.commands.User;

/**
 *
 * This plugin initializes a connection to an irc server by sending credentials.<br>
 * In the {@link Initialize} lifecycle, following parameters from the config will be checked: <br>
 * <ul>
 * <li>eva.irc.nick</li>
 * <li>jeva.irc.server</li>
 * <li>jeva.irc.port</li>
 * </ul>
 * If either one of those parameters is not set, a {@link RuntimeException} will be thrown, ending the whole
 * lifecycle.<br>
 * 
 * @author radiskull
 */
public class ConnectPlugin implements IrcHandlerPlugin {

  private final static Logger LOG = LoggerFactory.getLogger(ConnectPlugin.class);

  @Override
  public void initialize(IrcHandler handler) {
    var config = handler.getConfiguration();
    // TODO: Create a own configuration class, have all parameters and validation methods in there
    validateProperty(IrcHandler.PROP_NICK, config);
    validateProperty(IrcHandler.PROP_SERVER, config);
    validateProperty(IrcHandler.PROP_PORT, config);
  }

  private void validateProperty(String parameter, Properties config) {
    var value = config.getProperty(parameter);
    if (value == null || value.isBlank()) {
      // TODO: Throw a better exception, think about exception hierarchy
      throw new RuntimeException("Parameter '" + parameter + "' is not set! Please set the property in the config");
    }
  }

  @Override
  public void connect(IrcHandler handler) {
    var config = handler.getConfiguration();

    var nick = config.getProperty(IrcHandler.PROP_NICK);
    var password = config.getProperty(IrcHandler.PROP_PASSWORD);
    var mode = config.getProperty(IrcHandler.PROP_MODE, "8");
    var realName = config.getProperty(IrcHandler.PROP_REAL_NAME, "jEva");
    LOG.debug("Attempt connect with properties: [nick={}, password=***, mode={}, realName={}]", nick, mode, realName);

    if (password != null && !password.isBlank()) {
      handler.sendCommand(new Pass(password));
    }
    // TODO: handling problems when the nick is already in use
    handler.sendCommand(new Nick(nick));
    handler.sendCommand(new User(nick, mode, realName));
  }
}

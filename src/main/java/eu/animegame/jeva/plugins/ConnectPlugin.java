package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.core.exceptions.InitializationException;
import eu.animegame.jeva.core.exceptions.MissingParameterException;
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
    try {
      var config = handler.getConfig();
      config.verifyParameters(IrcConfig.PROP_NICK, IrcConfig.PROP_SERVER, IrcConfig.PROP_PORT);
    } catch (MissingParameterException e) {
      throw new InitializationException(e);
    }
  }

  @Override
  public void connect(IrcHandler handler) {
    var config = handler.getConfigProperties();

    var nick = config.getProperty(IrcConfig.PROP_NICK);
    var password = config.getProperty(IrcConfig.PROP_PASSWORD);
    var mode = config.getProperty(IrcConfig.PROP_MODE, "8");
    var realName = config.getProperty(IrcConfig.PROP_REAL_NAME, "jEva");
    LOG.debug("Attempt connect with properties: [nick={}, password=***, mode={}, realName={}]", nick, mode, realName);

    if (password != null && !password.isBlank()) {
      handler.sendCommand(new Pass(password));
    }
    // TODO: handling problems when the nick is already in use
    handler.sendCommand(new Nick(nick));
    handler.sendCommand(new User(nick, mode, realName));
  }
}

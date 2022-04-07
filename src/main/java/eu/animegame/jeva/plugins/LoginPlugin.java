package eu.animegame.jeva.plugins;

import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.irc.commands.Nick;
import eu.animegame.jeva.irc.commands.Pass;
import eu.animegame.jeva.irc.commands.User;

/**
 *
 * First step: send PASS if necessary<br>
 * Second step: send IRC nickname<br>
 * Third step: send user information<br>
 * 
 * @author radiskull
 */
public class LoginPlugin implements IrcHandlerPlugin {

  @Override
  public void connect(IrcHandler handler) {
    var config = handler.getConfiguration();

    var nick = config.getProperty(IrcHandler.PROP_NICK);
    var password = config.getProperty(IrcHandler.PROP_PASSWORD);
    var mode = config.getProperty(IrcHandler.PROP_MODE, "8");
    var realName = config.getProperty(IrcHandler.PROP_REAL_NAME, "jEva");

    if (password != null) {
      handler.sendCommand(new Pass(password));
    }
    handler.sendCommand(new Nick(nick));
    handler.sendCommand(new User(nick, mode, realName));
  }
}

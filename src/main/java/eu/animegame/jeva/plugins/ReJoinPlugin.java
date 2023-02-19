package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.core.exceptions.InitializationException;
import eu.animegame.jeva.core.exceptions.MissingParameterException;
import eu.animegame.jeva.irc.commands.Join;
import eu.animegame.jeva.irc.events.KickEvent;

public class ReJoinPlugin implements IrcHandlerPlugin {

  private static final Logger LOG = LoggerFactory.getLogger(ReJoinPlugin.class);

  @Override
  public void initialize(IrcHandler handler) {
    try {
      var config = handler.getConfig();
      config.verifyParameter(IrcConfig.PROP_NICK);
    } catch (MissingParameterException e) {
      throw new InitializationException(e);
    }
  }

  @IrcEventAcceptor(command = "KICK", clazz = KickEvent.class)
  public void rejoinChannel(KickEvent event, IrcHandler handler) {
    var nick = handler.getConfig().getProperty(IrcConfig.PROP_NICK, "");
    if (nick.equals(event.getKickedUser())) {
      LOG.info("Kicked from channel {}, trying to rejoin", event.getChannel());
      handler.sendCommand(new Join(event.getChannel()));
    }
  }
}

package eu.animegame.jeva.plugins;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.irc.CommandCode;
import eu.animegame.jeva.irc.commands.Join;
import eu.animegame.jeva.irc.events.InviteEvent;
import eu.animegame.jeva.irc.events.KickEvent;

/**
 *
 * @author radiskull
 */
// TODO: Make 3 plugins out of this one
@Deprecated
public class AutoJoinPlugin implements IrcHandlerPlugin {

  private static Logger LOG = LoggerFactory.getLogger(AutoJoinPlugin.class);

  private String[] channels;

  // TODO: also have a look into the config file (probably in the initialize lifecycle)
  public AutoJoinPlugin(String... channel) {
    this.channels = channel;
  }

  @IrcEventAcceptor(command = CommandCode.RPL_WELCOME)
  public void joinChannels(IrcBaseEvent event, IrcHandler handler) {
    LOG.info("Joining Channels {}", Arrays.toString(channels));
    handler.sendCommand(new Join(channels));
  }

  @IrcEventAcceptor(command = "KICK", clazz = KickEvent.class)
  public void rejoinChannel(KickEvent event, IrcHandler handler) {
    var nick = handler.getConfiguration().getProperty(IrcHandler.PROP_NICK);
    if (nick.equals(event.getKickedUser())) {
      LOG.info("Kicked from channel {}, trying to rejoin", event.getChannel());
      handler.sendCommand(new Join(event.getChannel()));
    }
  }

  @IrcEventAcceptor(command = "INVITE", clazz = InviteEvent.class)
  public void joinInvitation(InviteEvent event, IrcHandler handler) {
    var nick = handler.getConfiguration().getProperty(IrcHandler.PROP_NICK);
    if (nick.equals(event.getInvitedUser())) {
      LOG.info("Received invitation from {}, joining channel {}", event.getNickname(), event.getChannel());
      handler.sendCommand(new Join(event.getChannel()));
    }
  }
}

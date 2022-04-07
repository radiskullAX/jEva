package eu.animegame.jeva.plugins;

import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.irc.commands.Pong;

/**
 *
 * @author radiskull
 */
public class PingPlugin implements IrcHandlerPlugin {

  @IrcEventAcceptor(command = "PING")
  public void sendPong(IrcBaseEvent event, IrcHandler handler) {
    handler.sendCommand(new Pong(event.getParameters()));
  }
}

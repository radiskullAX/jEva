package eu.animegame.jeva.plugins;

import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.irc.commands.Pong;

/**
 *
 * This plugin sends back a PONG whenever a PING is send from an irc server.<br>
 * It is necessary to answer the PING or else the server will think that the client has timed out and close the
 * connection.<br>
 * 
 * @author radiskull
 */
public class PingPlugin implements IrcHandlerPlugin {

  @IrcEventAcceptor(command = "PING")
  public void sendPong(IrcBaseEvent event, IrcHandler handler) {
    handler.sendCommand(new Pong(event.getParameters()));
  }
}

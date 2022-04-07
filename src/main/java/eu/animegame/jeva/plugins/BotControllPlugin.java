package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.irc.commands.Join;
import eu.animegame.jeva.irc.commands.Part;
import eu.animegame.jeva.irc.events.PrivMsgEvent;

/**
 *
 * @author radiskull
 */
public class BotControllPlugin implements IrcHandlerPlugin {

  protected static Logger LOG = LoggerFactory.getLogger(BotControllPlugin.class);

  @IrcEventAcceptor(command = "PRIVMSG", clazz = PrivMsgEvent.class)
  public void parseInput(PrivMsgEvent event, IrcHandler handler) {
    if (event.getMessage().startsWith("!")) {
      var message = event.getMessage();
      var index = message.indexOf(0x20);
      var command = message.substring(1, index);
      executeCommand(command, event, handler);
    }
  }

  private void executeCommand(String command, PrivMsgEvent event, IrcHandler handler) {
    switch (command) {
      case "leave":
        executeLeave(handler, event);
        break;
      case "quit":
        executeQuit(handler, event);
        break;
      case "join":
        executeJoin(handler, event);
        break;
    }
  }

  private void executeJoin(IrcHandler handler, PrivMsgEvent event) {
    var message = event.getMessage();
    var firstSpace = message.indexOf(0x20);
    var channels = message.substring(firstSpace);
    // TODO: this has to be done better
    handler.sendCommand(new Join(channels));
    LOG.info("joining channel(s) {} on command of user {}", channels, event.getNickname());
  }

  private void executeQuit(IrcHandler handler, PrivMsgEvent event) {
    // TODO: have to think about it.
    handler.stop();
    LOG.info("quit irc on command of user {}", event.getNickname());
  }

  private void executeLeave(IrcHandler handler, PrivMsgEvent event) {
    handler.sendCommand(new Part(event.getChannel()));
    LOG.info("leaving channel {} on command of user {}", event.getChannel(), event.getNickname());
  }


}

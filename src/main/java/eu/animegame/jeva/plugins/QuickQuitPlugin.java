package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.irc.events.PrivMsgEvent;

public class QuickQuitPlugin implements IrcHandlerPlugin {

  protected static Logger LOG = LoggerFactory.getLogger(QuickQuitPlugin.class);

  @IrcEventAcceptor(command = "PRIVMSG", clazz = PrivMsgEvent.class)
  public void parseInput(PrivMsgEvent event, IrcHandler handler) throws Exception {
    if (event.getMessage().equalsIgnoreCase("!quit")) {
      LOG.info("recieved quit command from user {}", event.getNickname());
      throw new Exception("intenional quit because of user " + event.getNickname());
    }
  }
}

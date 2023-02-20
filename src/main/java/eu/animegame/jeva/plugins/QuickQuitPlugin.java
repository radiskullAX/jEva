package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcEventAcceptor;
import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.JEvaIrcPlugin;
import eu.animegame.jeva.irc.commands.PrivMsg;
import eu.animegame.jeva.irc.events.PrivMsgEvent;

public class QuickQuitPlugin implements JEvaIrcPlugin {

  protected static Logger LOG = LoggerFactory.getLogger(QuickQuitPlugin.class);

  @IrcEventAcceptor(command = PrivMsg.COMMAND)
  public void parseInput(PrivMsgEvent event, JEvaIrcClient jEvaClient) {
    if (event.getMessage().equalsIgnoreCase("!quit")) {
      LOG.info("recieved quit command from user {}", event.getNickname());
      jEvaClient.stop();
    }
  }
}

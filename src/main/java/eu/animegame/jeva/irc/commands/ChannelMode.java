package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class ChannelMode implements IrcCommand {

  public void sendChannelMode(String channel, String mode, String parameters) {
    var message = new StringBuilder();
    message.append("MODE ").append(channel).append(" ").append(mode).append(" ").append(parameters);
  }


  @Override
  public String build() {
    // TODO Auto-generated method stub
    return null;
  }

}

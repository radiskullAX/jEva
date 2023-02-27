package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 * 
 * @param target a channel or user
 * @param privMessage
 * 
 * @author radiskull
 */
public record PrivMsg(String target, String message) implements IrcCommand {

  public static final String COMMAND = "PRIVMSG";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target).append(" :").append(message);
    return command.toString();
  }
}

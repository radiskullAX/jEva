package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Kill(String nick, String comment) implements IrcCommand {

  public static final String COMMAND = "KILL";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick).append(" :").append(comment);
    return command.toString();
  }
}

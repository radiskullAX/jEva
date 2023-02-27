package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Error(String message) implements IrcCommand {

  public static final String COMMAND = "ERROR";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(message);
    return command.toString();
  }
}

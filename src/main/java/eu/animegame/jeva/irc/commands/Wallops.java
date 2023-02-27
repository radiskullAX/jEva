package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Wallops(String message) implements IrcCommand {

  public static final String COMMAND = "WALLOPS";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" :").append(message);
    return command.toString();
  }
}

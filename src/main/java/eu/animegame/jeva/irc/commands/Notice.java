package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Notice(String target, String message) implements IrcCommand {

  public static final String COMMAND = "NOTICE";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target).append(" :").append(message);
    return command.toString();
  }
}

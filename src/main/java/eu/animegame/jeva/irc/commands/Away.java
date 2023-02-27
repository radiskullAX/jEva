package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Away(String message) implements IrcCommand {

  public static final String COMMAND = "AWAY";

  /**
   * remove the away message
   */
  public Away() {
    this("");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!message.isBlank()) {
      command.append(" :").append(message);
    }
    return command.toString();
  }
}

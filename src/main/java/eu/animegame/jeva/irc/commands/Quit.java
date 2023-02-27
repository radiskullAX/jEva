package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Quit(String message) implements IrcCommand {

  public static final String COMMAND = "QUIT";

  public Quit() {
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

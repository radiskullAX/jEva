package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Time(String server) implements IrcCommand {

  public static final String COMMAND = "TIME";

  public Time() {
    this("");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

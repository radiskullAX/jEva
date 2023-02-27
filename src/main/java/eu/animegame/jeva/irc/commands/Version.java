package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Version(String server) implements IrcCommand {

  public static final String COMMAND = "VERSION";

  public Version() {
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

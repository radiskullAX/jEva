package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Motd(String server) implements IrcCommand {

  public static final String COMMAND = "MOTD";

  public Motd() {
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

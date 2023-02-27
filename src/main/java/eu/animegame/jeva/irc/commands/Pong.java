package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Pong(String pong, String server) implements IrcCommand {

  public static final String COMMAND = "PONG";

  public Pong(String pong) {
    this(pong, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(pong);
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

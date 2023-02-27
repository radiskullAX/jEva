package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Ping(String target, String server) implements IrcCommand {

  public static final String COMMAND = "PING";

  public Ping(String target) {
    this(target, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target);
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Lusers(String mask, String server) implements IrcCommand {

  public static final String COMMAND = "LUSERS";

  public Lusers() {
    this("", "");
  }

  public Lusers(String mask) {
    this(mask, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!mask.isBlank()) {
      command.append(" ").append(mask);
    }
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

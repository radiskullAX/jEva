package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Links(String server, String mask) implements IrcCommand {

  public static final String COMMAND = "LINKS";

  public Links() {
    this("", "");
  }

  public Links(String mask) {
    this("", mask);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    if (!mask.isBlank()) {
      command.append(" ").append(mask);
    }
    return command.toString();
  }
}

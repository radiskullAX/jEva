package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Users(String server) implements IrcCommand {

  public static final String COMMAND = "USERS";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(server);
    return command.toString();
  }
}

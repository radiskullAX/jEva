package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Users implements IrcCommand {

  public static final String COMMAND = "USERS";

  private final String server;

  public Users(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(server);
    return command.toString();
  }
}

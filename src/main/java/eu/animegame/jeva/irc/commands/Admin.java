package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Admin implements IrcCommand {

  private final String server;

  public Admin() {
    this(null);
  }

  public Admin(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("ADMIN");
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

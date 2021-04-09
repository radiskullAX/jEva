package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Version implements IrcCommand {

  private final String server;

  public Version() {
    this(null);
  }

  public Version(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("VERSION");
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

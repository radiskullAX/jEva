package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Motd implements IrcCommand {

  private final String server;

  public Motd() {
    this(null);
  }

  public Motd(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("MOTD");
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

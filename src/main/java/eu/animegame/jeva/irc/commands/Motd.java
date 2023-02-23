package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Motd implements IrcCommand {

  public static final String COMMAND = "MOTD";

  private final String server;

  public Motd() {
    this(null);
  }

  public Motd(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

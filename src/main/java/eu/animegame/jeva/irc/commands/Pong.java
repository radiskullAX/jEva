package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Pong implements IrcCommand {

  private final String pong;

  private final String server;

  public Pong(String pong) {
    this(pong, null);
  }

  public Pong(String pong, String server) {
    this.pong = pong;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("PONG ").append(pong);
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

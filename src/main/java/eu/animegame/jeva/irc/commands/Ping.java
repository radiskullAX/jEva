package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Ping implements IrcCommand {

  private final String target;

  private final String server;

  public Ping(String target) {
    this(target, null);
  }

  public Ping(String target, String server) {
    this.target = target;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("PING ").append(target);
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

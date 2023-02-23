package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Ping implements IrcCommand {

  public static final String COMMAND = "PING";

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
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target);
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

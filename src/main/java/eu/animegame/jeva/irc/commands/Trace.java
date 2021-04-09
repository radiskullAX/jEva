package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Trace implements IrcCommand {

  private final String server;

  public Trace() {
    this(null);
  }

  public Trace(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("TRACE");
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

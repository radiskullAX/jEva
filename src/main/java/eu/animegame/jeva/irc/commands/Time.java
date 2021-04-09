package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Time implements IrcCommand {

  private final String server;

  public Time() {
    this(null);
  }

  public Time(String server) {
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("TIME");
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Time implements IrcCommand {

  public static final String COMMAND = "TIME";

  private final String server;

  public Time() {
    this(null);
  }

  public Time(String server) {
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

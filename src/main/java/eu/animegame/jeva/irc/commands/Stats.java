package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Stats implements IrcCommand {

  public static final String COMMAND = "STATS";

  private final String server;

  private final String query;

  public Stats(String server) {
    this(null, server);
  }

  /**
   * l - returns a list of the server's connections, showing how long each connection has been established and the
   * traffic over that connection in Kbytes and messages for each direction<br>
   * m - returns the usage count for each of commands supported by the server; commands for which the usage count is
   * zero MAY be omitted<br>
   * o - returns a list of configured privileged users, operators<br>
   * u - returns a string showing how long the server has been up<br>
   */
  public Stats(String query, String server) {
    this.server = server;
    this.query = query;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (query != null) {
      command.append(" ").append(query);
    }
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

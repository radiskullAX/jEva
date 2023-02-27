package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * l - returns a list of the server's connections, showing how long each connection has been established and the traffic
 * over that connection in Kbytes and messages for each direction<br>
 * m - returns the usage count for each of commands supported by the server; commands for which the usage count is zero
 * MAY be omitted<br>
 * o - returns a list of configured privileged users, operators<br>
 * u - returns a string showing how long the server has been up<br>
 * 
 * @author radiskull
 */
public record Stats(String query, String server) implements IrcCommand {

  public static final String COMMAND = "STATS";

  public Stats(String server) {
    this("", server);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!query.isBlank()) {
      command.append(" ").append(query);
    }
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

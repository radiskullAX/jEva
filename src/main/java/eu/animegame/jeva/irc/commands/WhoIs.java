package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class WhoIs implements IrcCommand {

  public static final String COMMAND = "WHOIS";

  private final String user;

  private final String server;

  public WhoIs(String user) {
    this(user, null);
  }

  public WhoIs(String[] users) {
    this(Arrays.stream(users).collect(joining(",")), null);
  }

  public WhoIs(String[] users, String server) {
    this(Arrays.stream(users).collect(joining(",")), server);
  }

  public WhoIs(String user, String server) {
    this.user = user;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (server != null) {
      command.append(" ").append(server);
    }
    command.append(" ").append(user);
    return command.toString();
  }
}

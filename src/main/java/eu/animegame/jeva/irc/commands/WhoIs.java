package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record WhoIs(String user, String server) implements IrcCommand {

  public static final String COMMAND = "WHOIS";

  public WhoIs(String user) {
    this(user, "");
  }

  public WhoIs(String[] users) {
    this(Arrays.stream(users).collect(joining(",")), "");
  }

  public WhoIs(String[] users, String server) {
    this(Arrays.stream(users).collect(joining(",")), server);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    command.append(" ").append(user);
    return command.toString();
  }
}

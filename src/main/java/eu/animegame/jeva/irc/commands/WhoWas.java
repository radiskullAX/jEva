package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record WhoWas(String nick, int count, String server) implements IrcCommand {

  public static final String COMMAND = "WHOWAS";

  public WhoWas(String nick) {
    this(nick, 0, "");
  }

  public WhoWas(String[] nicks) {
    this(Arrays.stream(nicks).collect(joining(",")), 0, "");
  }

  public WhoWas(String nick, int count) {
    this(nick, count, "");
  }

  public WhoWas(String[] nicks, int count) {
    this(Arrays.stream(nicks).collect(joining(",")), count, "");
  }

  public WhoWas(String[] nicks, int count, String server) {
    this(Arrays.stream(nicks).collect(joining(",")), count, server);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    if (count > 0) {
      command.append(" ").append(count);
    }
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

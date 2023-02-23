package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class WhoWas implements IrcCommand {

  public static final String COMMAND = "WHOWAS";

  private final String nick;

  private final int count;

  private final String server;

  public WhoWas(String nick) {
    this(nick, 0, null);
  }

  public WhoWas(String[] nicks) {
    this(Arrays.stream(nicks).collect(joining(",")), 0, null);
  }

  public WhoWas(String nick, int count) {
    this(nick, count, null);
  }

  public WhoWas(String[] nicks, int count) {
    this(Arrays.stream(nicks).collect(joining(",")), count, null);
  }

  public WhoWas(String[] nicks, int count, String server) {
    this(Arrays.stream(nicks).collect(joining(",")), count, server);
  }

  public WhoWas(String nick, int count, String server) {
    this.nick = nick;
    this.count = count;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    if (count > 0) {
      command.append(" ").append(count);
    }
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

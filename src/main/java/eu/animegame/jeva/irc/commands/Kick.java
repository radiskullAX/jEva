package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Kick implements IrcCommand {

  public static final String COMMAND = "KICK";

  private final String user;

  private final String channel;

  private final String message;

  public Kick(String channel, String user) {
    this(channel, user, null);
  }

  public Kick(String[] channels, String[] users) {
    this(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")), null);
  }

  public Kick(String[] channels, String[] users, String message) {
    this(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")), message);
  }

  public Kick(String channel, String user, String message) {
    this.channel = channel;
    this.user = user;
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel).append(" ").append(user);
    if (message != null) {
      command.append(" :").append(message);
    }
    return command.toString();
  }
}

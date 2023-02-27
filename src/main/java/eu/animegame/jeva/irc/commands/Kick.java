package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Kick(String channel, String user, String message) implements IrcCommand {

  public static final String COMMAND = "KICK";

  public Kick(String channel, String user) {
    this(channel, user, "");
  }

  public Kick(String[] channels, String[] users) {
    this(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")), "");
  }

  public Kick(String[] channels, String[] users, String message) {
    this(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")), message);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel).append(" ").append(user);
    if (!message.isBlank()) {
      command.append(" :").append(message);
    }
    return command.toString();
  }
}

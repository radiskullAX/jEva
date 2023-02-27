package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Names(String channel, String server) implements IrcCommand {

  public static final String COMMAND = "NAMES";

  /**
   * list all channels + users
   */
  public Names() {
    this("", "");
  }

  public Names(String channel) {
    this(channel, "");
  }

  public Names(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), "");
  }

  public Names(String[] channels, String server) {
    this(Arrays.stream(channels).collect(joining(",")), server);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!channel.isBlank()) {
      command.append(" ").append(channel);
    }
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

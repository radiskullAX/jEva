package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record List(String channel, String server) implements IrcCommand {

  public static final String COMMAND = "LIST";

  /**
   * list all channels
   */
  public List() {
    this("", "");
  }

  public List(String channel) {
    this(channel, "");
  }

  public List(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), "");
  }

  public List(String[] channels, String server) {
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

package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Part(String channel, String message) implements IrcCommand {

  public static final String COMMAND = "PART";

  public Part(String channel) {
    this(channel, "");
  }

  public Part(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), "");
  }

  public Part(String[] channels, String message) {
    this(Arrays.stream(channels).collect(joining(",")), message);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel);
    if (!message.isBlank()) {
      command.append(" :").append(message);
    }
    return command.toString();
  }
}

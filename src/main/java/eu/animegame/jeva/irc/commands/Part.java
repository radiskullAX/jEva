package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Part implements IrcCommand {

  public static final String COMMAND = "PART";

  private final String channel;

  private final String message;

  public Part(String channel) {
    this(channel, null);
  }

  public Part(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), null);
  }

  public Part(String[] channels, String message) {
    this(Arrays.stream(channels).collect(joining(",")), message);
  }

  public Part(String channel, String message) {
    this.channel = channel;
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel);
    if (message != null) {
      command.append(" :").append(message);
    }
    return command.toString();
  }

}

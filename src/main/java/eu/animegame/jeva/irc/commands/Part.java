package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Part implements IrcCommand {

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
    var command = new StringBuilder();
    command.append("PART ").append(channel);
    if (message != null) {
      command.append(" :").append(message);
    }
    return command.toString();
  }

}

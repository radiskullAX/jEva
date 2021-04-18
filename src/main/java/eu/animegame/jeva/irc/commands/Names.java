package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Names implements IrcCommand {

  private final String channel;

  private final String server;

  /**
   * list all channels + users
   */
  public Names() {
    this.channel = null;
    this.server = null;
  }

  public Names(String channel) {
    this(channel, null);
  }

  public Names(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), null);
  }

  public Names(String[] channels, String server) {
    this(Arrays.stream(channels).collect(joining(",")), server);
  }

  public Names(String channel, String server) {
    this.channel = channel;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("NAMES");
    if (channel != null) {
      command.append(" ").append(channel);
    }
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }
}

package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class List implements IrcCommand {

  public static final String COMMAND = "LIST";

  private final String channel;

  private final String server;

  /**
   * list all channels
   */
  public List() {
    this.channel = null;
    this.server = null;
  }

  public List(String channel) {
    this(channel, null);
  }

  public List(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), null);
  }

  public List(String[] channels, String server) {
    this(Arrays.stream(channels).collect(joining(",")), server);
  }

  public List(String channel, String server) {
    this.channel = channel;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (channel != null) {
      command.append(" ").append(channel);
    }
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

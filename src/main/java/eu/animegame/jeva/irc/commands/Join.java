package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Join implements IrcCommand {

  private final String channel;

  private final String password;

  public Join() {
    this("0", null);
  }

  public Join(String channel) {
    this(channel, null);
  }

  public Join(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), null);
  }

  /**
   * channels with passwords should come first
   * 
   * @param channels
   * @param keys
   */
  public Join(String[] channels, String[] passwords) {
    this(Arrays.stream(channels).collect(joining(",")), Arrays.stream(passwords).collect(joining(",")));
  }

  public Join(String channel, String password) {
    this.channel = channel;
    this.password = password;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("JOIN ").append(channel);
    if (password != null) {
      command.append(" ").append(password);
    }
    return command.toString();
  }
}

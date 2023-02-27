package eu.animegame.jeva.irc.commands;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import java.util.List;
import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Join(String channel, String password) implements IrcCommand {

  public static final String COMMAND = "JOIN";

  public Join() {
    this("0", "");
  }

  public Join(String channel) {
    this(channel, "");
  }

  public Join(String[] channels) {
    this(Arrays.stream(channels).collect(joining(",")), "");
  }

  public Join(List<String> channels) {
    this(channels.stream().collect(joining(",")), "");
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

  /**
   * channels with passwords should come first
   * 
   * @param channels
   * @param keys
   */
  public Join(List<String> channels, List<String> passwords) {
    this(channels.stream().collect(joining(",")), passwords.stream().collect(joining(",")));
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel);
    if (!password.isBlank()) {
      command.append(" ").append(password);
    }
    return command.toString();
  }
}

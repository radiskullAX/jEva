package eu.animegame.jeva.irc.commands;

import static eu.animegame.jeva.irc.ModeSetting.NONE;
import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.irc.ModeSetting;

/**
 *
 * channel modes specified in RFC 2811.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2811#section-4">RFC 2811 s 4</a>
 *
 *
 * @author radiskull
 */
public record ChannelMode(String channel, ModeSetting setting, String mode, String parameters) implements IrcCommand {

  public static final String COMMAND = "MODE";

  public ChannelMode(String channel, String mode) {
    this(channel, NONE, mode, "");
  }

  public ChannelMode(String channel, ModeSetting setting, String mode) {
    this(channel, setting, mode, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel).append(" ");
    if (!NONE.equals(setting)) {
      command.append(setting);
    }
    command.append(mode);
    if (!parameters.isBlank()) {
      command.append(" ").append(parameters);
    }
    return command.toString();
  }
}

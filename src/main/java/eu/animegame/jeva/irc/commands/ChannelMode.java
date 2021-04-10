package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.irc.ModeSetting;

/**
 *
 * @author radiskull
 */
public final class ChannelMode implements IrcCommand {

  private final String channel;

  private final ModeSetting setting;

  private final String mode;

  private final String parameters;

  public ChannelMode(String channel, String mode) {
    this(channel, null, mode, null);
  }

  public ChannelMode(String channel, ModeSetting setting, String mode) {
    this(channel, setting, mode, null);
  }

  public ChannelMode(String channel, ModeSetting setting, String mode, String parameters) {
    this.channel = channel;
    this.setting = setting;
    this.mode = mode;
    this.parameters = parameters;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("MODE ").append(channel).append(" ");
    if (setting != null) {
      command.append(setting.getValue());
    }
    command.append(mode);
    if (parameters != null) {
      command.append(" ").append(parameters);
    }
    return command.toString();
  }

}

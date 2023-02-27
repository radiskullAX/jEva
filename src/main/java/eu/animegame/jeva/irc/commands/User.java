package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 * modes: 4 (w) / 8 (i) / 12 (wi)
 * 
 * @param nick
 * @param mode
 * @param realName
 *
 * @author radiskull
 */
public record User(String nick, String mode, String realName) implements IrcCommand {

  public static final String COMMAND = "USER";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick).append(" ").append(mode).append(" * :").append(realName);
    return command.toString();
  }
}

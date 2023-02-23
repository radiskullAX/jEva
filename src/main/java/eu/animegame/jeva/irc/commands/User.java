package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class User implements IrcCommand {

  public static final String COMMAND = "USER";

  private final String nick;

  private final String mode;

  private final String realName;

  /**
   * modes: 4 (w) / 8 (i) / 12 (wi)
   * 
   * @param nick
   * @param mode
   * @param realName
   */
  public User(String nick, String mode, String realName) {
    this.nick = nick;
    this.mode = mode;
    this.realName = realName;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick).append(" ").append(mode).append(" * :").append(realName);
    return command.toString();
  }

}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class User implements IrcCommand {

  private final String nick;

  private final int mode;

  private final String realName;

  /**
   * modes: 2 (w) / 4 (i) / 6 (wi)
   * 
   * @param nick
   * @param mode
   * @param realName
   */
  public User(String nick, int mode, String realName) {
    this.nick = nick;
    this.mode = mode;
    this.realName = realName;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("USER ").append(nick).append(" ").append(mode).append(" * :").append(realName);
    return command.toString();
  }

}

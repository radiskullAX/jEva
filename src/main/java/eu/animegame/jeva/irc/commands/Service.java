package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public class Service implements IrcCommand {

  private final String nick;

  private final String serverMask;

  private final String info;

  public Service(String nick, String serverMask, String info) {
    this.nick = nick;
    this.serverMask = serverMask;
    this.info = info;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("SERVICE ").append(nick).append(" * ").append(serverMask).append(" 0 0 :").append(info);
    return command.toString();
  }

}

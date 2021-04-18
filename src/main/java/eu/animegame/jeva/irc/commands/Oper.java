package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Oper implements IrcCommand {

  private final String user;

  private final String password;

  public Oper(String user, String password) {
    this.user = user;
    this.password = password;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("OPER ").append(user).append(" ").append(password);
    return command.toString();
  }

}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Oper(String user, String password) implements IrcCommand {

  public static final String COMMAND = "OPER";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(user).append(" ").append(password);
    return command.toString();
  }
}

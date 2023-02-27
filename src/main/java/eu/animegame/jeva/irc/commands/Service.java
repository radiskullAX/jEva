package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Service(String nick, String serverMask, String info) implements IrcCommand {

  public static final String COMMAND = "SERVICE";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick).append(" * ").append(serverMask).append(" 0 0 :").append(info);
    return command.toString();
  }

}

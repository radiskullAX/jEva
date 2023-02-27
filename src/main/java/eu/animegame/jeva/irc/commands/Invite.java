package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Invite(String user, String channel) implements IrcCommand {

  public static final String COMMAND = "INVITE";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(user).append(" ").append(channel);
    return command.toString();
  }
}

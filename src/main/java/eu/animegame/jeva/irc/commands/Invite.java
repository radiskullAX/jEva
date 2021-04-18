package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Invite implements IrcCommand {

  private final String user;

  private final String channel;

  public Invite(String user, String channel) {
    this.user = user;
    this.channel = channel;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("INVITE ").append(user).append(" ").append(channel);
    return command.toString();
  }
}

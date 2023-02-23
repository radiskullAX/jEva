package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Kill implements IrcCommand {

  public static final String COMMAND = "KILL";

  private final String nick;

  private final String comment;

  public Kill(String nick, String comment) {
    this.nick = nick;
    this.comment = comment;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick).append(" :").append(comment);
    return command.toString();
  }
}

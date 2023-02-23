package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Nick implements IrcCommand {

  public static final String COMMAND = "NICK";

  private final String nick;

  public Nick(String nick) {
    this.nick = nick;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    return command.toString();
  }

}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Nick implements IrcCommand {

  private final String nick;

  public Nick(String nick) {
    this.nick = nick;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("NICK ").append(nick);
    return command.toString();
  }

}

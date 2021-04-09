package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Quit implements IrcCommand {

  private final String message;

  public Quit() {
    this(null);
  }

  public Quit(String message) {
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("QUIT");
    if (message != null) {
      command.append(" :").append(message);
    }
    return command.toString();
  }

}

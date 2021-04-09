package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Error implements IrcCommand {

  private final String message;

  public Error(String message) {
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("ERROR ").append(message);
    return command.toString();
  }

}

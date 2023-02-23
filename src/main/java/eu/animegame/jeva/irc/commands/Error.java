package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Error implements IrcCommand {

  public static final String COMMAND = "ERROR";

  private final String message;

  public Error(String message) {
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(message);
    return command.toString();
  }

}

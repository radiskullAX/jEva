package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Wallops implements IrcCommand {

  private final String message;

  public Wallops(String message) {
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("WALLOPS :").append(message);
    return command.toString();
  }

}

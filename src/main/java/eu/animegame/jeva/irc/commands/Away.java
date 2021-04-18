package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Away implements IrcCommand {

  private final String message;

  /**
   * remove the away message
   */
  public Away() {
    this(null);
  }

  public Away(String message) {
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder("AWAY");
    if (message != null) {
      command.append(" :").append(message);
    }
    return command.toString();
  }

}

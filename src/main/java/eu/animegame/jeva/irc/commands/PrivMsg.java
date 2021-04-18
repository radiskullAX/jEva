package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class PrivMsg implements IrcCommand {

  private final String target;

  private final String message;

  /**
   * 
   * @param target a channel or user
   * @param privMessage
   */
  public PrivMsg(String target, String message) {
    this.target = target;
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("PRIVMSG ").append(target).append(" :").append(message);
    return command.toString();
  }

}

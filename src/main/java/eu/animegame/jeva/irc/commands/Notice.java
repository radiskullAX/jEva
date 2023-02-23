package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Notice implements IrcCommand {

  public static final String COMMAND = "NOTICE";

  private final String target;

  private final String message;

  public Notice(String target, String message) {
    this.target = target;
    this.message = message;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target).append(" :").append(message);
    return command.toString();
  }

}

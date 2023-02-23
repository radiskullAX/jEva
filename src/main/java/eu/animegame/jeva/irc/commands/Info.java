package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Info implements IrcCommand {

  public static final String COMMAND = "INFO";

  private final String target;

  public Info(String target) {
    this.target = target;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target);
    return command.toString();
  }

}

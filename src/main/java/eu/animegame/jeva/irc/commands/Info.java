package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Info implements IrcCommand {

  private final String target;

  public Info(String target) {
    this.target = target;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("INFO ").append(target);
    return command.toString();
  }

}

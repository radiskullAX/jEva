package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Who implements IrcCommand {

  private final String mask;

  private final boolean operatorsOnly;

  public Who(boolean operatorsOnly) {
    this("0", operatorsOnly);
  }

  public Who(String mask, boolean operatorsOnly) {
    this.mask = mask;
    this.operatorsOnly = operatorsOnly;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("WHO ").append(mask);
    if (operatorsOnly) {
      command.append(" ").append("o");
    }
    return command.toString();
  }
}

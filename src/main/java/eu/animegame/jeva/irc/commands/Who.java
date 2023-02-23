package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Who implements IrcCommand {

  public static final String COMMAND = "WHO";

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
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(mask);
    if (operatorsOnly) {
      command.append(" ").append("o");
    }
    return command.toString();
  }
}

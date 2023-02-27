package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Who(String mask, boolean operatorsOnly) implements IrcCommand {

  public static final String COMMAND = "WHO";

  public Who(boolean operatorsOnly) {
    this("0", operatorsOnly);
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

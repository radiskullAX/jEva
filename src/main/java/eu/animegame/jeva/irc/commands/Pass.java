package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Pass(String password) implements IrcCommand {

  public static final String COMMAND = "PASS";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(password);
    return command.toString();
  }
}

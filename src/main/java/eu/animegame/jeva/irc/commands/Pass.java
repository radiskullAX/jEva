package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Pass implements IrcCommand {

  public static final String COMMAND = "PASS";

  private final String password;

  public Pass(String password) {
    this.password = password;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(password);
    return command.toString();
  }

}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Pass implements IrcCommand {

  private final String password;

  public Pass(String password) {
    this.password = password;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("PASS ").append(password);
    return command.toString();
  }

}

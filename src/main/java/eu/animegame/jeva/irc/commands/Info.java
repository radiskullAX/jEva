package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Info(String target) implements IrcCommand {

  public static final String COMMAND = "INFO";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(target);
    return command.toString();
  }
}

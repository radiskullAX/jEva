package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Restart() implements IrcCommand {

  public static final String COMMAND = "RESTART";

  @Override
  public String build() {
    return COMMAND;
  }
}

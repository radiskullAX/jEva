package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Die() implements IrcCommand {

  public static final String COMMAND = "DIE";

  @Override
  public String build() {
    return COMMAND;
  }
}

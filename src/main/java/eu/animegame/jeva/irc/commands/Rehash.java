package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Rehash() implements IrcCommand {

  public static final String COMMAND = "REHASH";

  @Override
  public String build() {
    return COMMAND;
  }
}

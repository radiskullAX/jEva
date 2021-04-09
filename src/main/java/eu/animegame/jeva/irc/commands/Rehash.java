package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Rehash implements IrcCommand {

  @Override
  public String build() {
    return "REHASH";
  }

}

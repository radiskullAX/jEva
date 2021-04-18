package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Restart implements IrcCommand {

  @Override
  public String build() {
    return "RESTART";
  }

}

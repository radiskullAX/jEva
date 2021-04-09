package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Lusers implements IrcCommand {

  private final String mask;

  private final String server;

  public Lusers() {
    this(null, null);
  }

  public Lusers(String mask) {
    this(mask, null);
  }

  public Lusers(String mask, String server) {
    this.mask = mask;
    this.server = server;
  }

  @Override
  public String build() {
    var command = new StringBuilder("LUSERS");
    if (mask != null) {
      command.append(" ").append(mask);
    }
    if (server != null) {
      command.append(" ").append(server);
    }
    return command.toString();
  }

}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Links implements IrcCommand {

  private final String server;

  private final String mask;

  public Links() {
    this(null, null);
  }

  public Links(String mask) {
    this(null, mask);
  }

  public Links(String server, String mask) {
    this.server = server;
    this.mask = mask;
  }

  @Override
  public String build() {
    var command = new StringBuilder("LINKS");
    if (server != null) {
      command.append(" ").append(server);
    }
    if (mask != null) {
      command.append(" ").append(mask);
    }
    return command.toString();
  }

}

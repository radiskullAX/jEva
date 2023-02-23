package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class ServList implements IrcCommand {

  public static final String COMMAND = "SERVLIST";

  private final String mask;

  private final String type;

  public ServList() {
    this(null, null);
  }

  public ServList(String mask) {
    this(mask, null);
  }

  public ServList(String mask, String type) {
    this.mask = mask;
    this.type = type;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (mask != null) {
      command.append(" ").append(mask);
    }
    if (type != null) {
      command.append(" ").append(type);
    }
    return command.toString();
  }
}

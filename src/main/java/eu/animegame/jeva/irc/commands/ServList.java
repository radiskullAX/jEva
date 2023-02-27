package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record ServList(String mask, String type) implements IrcCommand {

  public static final String COMMAND = "SERVLIST";

  public ServList() {
    this("", "");
  }

  public ServList(String mask) {
    this(mask, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    if (!mask.isBlank()) {
      command.append(" ").append(mask);
    }
    if (!type.isBlank()) {
      command.append(" ").append(type);
    }
    return command.toString();
  }
}

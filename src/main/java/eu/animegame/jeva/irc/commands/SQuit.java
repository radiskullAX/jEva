package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record SQuit(String server, String comment) implements IrcCommand {

  public static final String COMMAND = "SQUIT";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(server).append(" :").append(comment);
    return command.toString();
  }
}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class SQuit implements IrcCommand {

  public static final String COMMAND = "SQUIT";

  private final String server;

  private final String comment;

  public SQuit(String server, String comment) {
    this.server = server;
    this.comment = comment;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(server).append(" :").append(comment);
    return command.toString();
  }

}

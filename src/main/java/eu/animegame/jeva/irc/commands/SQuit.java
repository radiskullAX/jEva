package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class SQuit implements IrcCommand {

  private final String server;

  private final String comment;

  public SQuit(String server, String comment) {
    this.server = server;
    this.comment = comment;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("SQUIT ").append(server).append(" :").append(comment);
    return command.toString();
  }

}

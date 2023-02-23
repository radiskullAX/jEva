package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Summon implements IrcCommand {

  public static final String COMMAND = "SUMMON";

  private final String nick;

  private final String server;

  private final String channel;

  public Summon(String nick) {
    this(nick, null, null);
  }

  public Summon(String nick, String server) {
    this(nick, server, null);
  }

  public Summon(String nick, String server, String channel) {
    this.nick = nick;
    this.server = server;
    this.channel = channel;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    if (server != null) {
      command.append(" ").append(server);
    }
    if (channel != null) {
      command.append(" ").append(channel);
    }
    return command.toString();
  }
}

package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Summon(String nick, String server, String channel) implements IrcCommand {

  public static final String COMMAND = "SUMMON";

  public Summon(String nick) {
    this(nick, "", "");
  }

  public Summon(String nick, String server) {
    this(nick, server, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    if (!server.isBlank()) {
      command.append(" ").append(server);
    }
    if (!channel.isBlank()) {
      command.append(" ").append(channel);
    }
    return command.toString();
  }
}

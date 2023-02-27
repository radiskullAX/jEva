package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record Connect(String server, int port, String remoteServer) implements IrcCommand {

  public static final String COMMAND = "CONNECT";

  public Connect(String server, int port) {
    this(server, port, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(server).append(" ").append(port);
    if (!remoteServer.isBlank()) {
      command.append(" ").append(remoteServer);
    }
    return command.toString();
  }
}

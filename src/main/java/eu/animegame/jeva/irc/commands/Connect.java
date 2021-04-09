package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Connect implements IrcCommand {

  private final String server;

  private final int port;

  private final String remoteServer;

  public Connect(String server, int port) {
    this(server, port, null);
  }

  public Connect(String server, int port, String remoteServer) {
    this.server = server;
    this.port = port;
    this.remoteServer = remoteServer;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("CONNECT ").append(server).append(" ").append(port);
    if (remoteServer != null) {
      command.append(" ").append(remoteServer);
    }
    return command.toString();
  }

}

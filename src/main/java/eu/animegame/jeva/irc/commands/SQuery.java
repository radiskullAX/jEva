package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class SQuery implements IrcCommand {

  private final String serviceName;

  private final String serviceMessage;

  public SQuery(String serviceName, String serviceMessage) {
    this.serviceName = serviceName;
    this.serviceMessage = serviceMessage;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("SQUERY ").append(serviceName).append(" :").append(serviceMessage);
    return command.toString();
  }
}

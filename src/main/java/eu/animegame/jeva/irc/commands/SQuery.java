package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class SQuery implements IrcCommand {

  public static final String COMMAND = "SQUERY";

  private final String serviceName;

  private final String serviceMessage;

  public SQuery(String serviceName, String serviceMessage) {
    this.serviceName = serviceName;
    this.serviceMessage = serviceMessage;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(serviceName).append(" :").append(serviceMessage);
    return command.toString();
  }
}

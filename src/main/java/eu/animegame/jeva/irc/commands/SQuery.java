package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public record SQuery(String serviceName, String serviceMessage) implements IrcCommand {

  public static final String COMMAND = "SQUERY";

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(serviceName).append(" :").append(serviceMessage);
    return command.toString();
  }
}

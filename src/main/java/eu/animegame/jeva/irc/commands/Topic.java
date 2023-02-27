package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 * @param channel
 * @param topic if empty, clear the topic on channel
 *
 * @author radiskull
 */
public record Topic(String channel, String topic) implements IrcCommand {

  public static final String COMMAND = "TOPIC";

  /**
   * check the topic
   */
  public Topic(String channel) {
    this(channel, "");
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(channel);
    if (!topic.isBlank()) {
      command.append(" :").append(topic);
    }
    return command.toString();
  }
}

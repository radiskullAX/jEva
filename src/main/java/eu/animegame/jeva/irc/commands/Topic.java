package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;

/**
 *
 * @author radiskull
 */
public final class Topic implements IrcCommand {

  private final String channel;

  private final String topic;

  /**
   * check the topic
   */
  public Topic(String channel) {
    this(channel, null);
  }

  /**
   * set a topic
   * 
   * @param channel
   * @param topic if empty, clear the topic on channel
   */
  public Topic(String channel, String topic) {
    this.channel = channel;
    this.topic = topic;
  }

  @Override
  public String build() {
    var command = new StringBuilder();
    command.append("TOPIC ").append(channel);
    if (topic != null) {
      command.append(" :").append(topic);
    }
    return command.toString();
  }
}

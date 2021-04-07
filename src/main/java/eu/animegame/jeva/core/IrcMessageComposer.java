package eu.animegame.jeva.core;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import java.util.stream.Stream;
import eu.animegame.jeva.interfaces.IrcHandler;

/**
 *
 * @author radiskull
 */
// TODO: write Javadoc :)
class IrcMessageComposer {

  protected IrcHandler handler;

  public IrcMessageComposer(IrcHandler handler) {
    this.handler = handler;
  }

  public void sendPass(String password) {
    StringBuilder message = new StringBuilder();
    message.append("PASS ").append(password);
    handler.sendMessage(message.toString());
  }

  public void sendNick(String nick) {
    StringBuilder message = new StringBuilder();
    message.append("NICK ").append(nick);
    handler.sendMessage(message.toString());
  }

  public void sendUser(String user, int mode, String realName) {
    StringBuilder message = new StringBuilder();
    message.append("USER ").append(user).append(" ").append(mode).append(" :").append(realName);
    handler.sendMessage(message.toString());
  }

  public void sendOper(String name, String password) {
    StringBuilder message = new StringBuilder();
    message.append("OPER ").append(name).append(" ").append(password);
    handler.sendMessage(message.toString());
  }

  public void sendUserMode(String nick, String mode) {
    StringBuilder message = new StringBuilder();
    message.append("MODE ").append(nick).append(" ").append(mode);
    handler.sendMessage(message.toString());
  }

  public void sendService(String nick, String distribution) {
    // TODO: implement sendService(), do some research before hand
  }

  public void sendQuit() {
    handler.sendMessage("QUIT");
  }

  public void sendQuit(String quitMessage) {
    StringBuilder message = new StringBuilder("QUIT");
    message.append("QUIT").append(" :").append(quitMessage);
    handler.sendMessage(message.toString());
  }

  public void sendServerQuit(String server, String comment) {
    StringBuilder message = new StringBuilder();
    message.append("SQUIT ").append(server).append(" :").append(comment);
    handler.sendMessage(message.toString());
  }

  public void sendJoin(String channel) {
    StringBuilder message = new StringBuilder();
    message.append("JOIN ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendJoin(String channel, String key) {
    StringBuilder message = new StringBuilder();
    message.append("JOIN ").append(channel).append(" ").append(key);
    handler.sendMessage(message.toString());
  }

  public void sendJoin(String[] channels) {
    StringBuilder message = new StringBuilder("JOIN ");
    message.append(Arrays.stream(channels).collect(joining(",")));
    handler.sendMessage(message.toString());
  }

  /**
   * channels with passwords should come first
   * 
   * @param channels
   * @param keys
   */
  public void sendJoin(String[] channels, String[] keys) {
    StringBuilder message = new StringBuilder("JOIN ");
    message.append(Arrays.stream(channels).collect(joining(",")));
    message.append(" ");
    message.append(Arrays.stream(keys).collect(joining(",")));
    handler.sendMessage(message.toString());
  }

  public void sendPart(String channel) {
    StringBuilder message = new StringBuilder();
    message.append("PART ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendPart(String[] channels) {
    StringBuilder message = new StringBuilder();
    message.append("PART ").append(Arrays.stream(channels).collect(joining(",")));
    handler.sendMessage(message.toString());
  }

  public void sendPart(String channel, String partMessage) {
    StringBuilder message = new StringBuilder();
    message.append("PART ").append(channel).append(" :").append(partMessage);
    handler.sendMessage(message.toString());
  }

  public void sendPart(String[] channels, String partMessage) {
    StringBuilder message = new StringBuilder();
    message.append("PART ").append(Arrays.stream(channels).collect(joining(","))).append(" :").append(partMessage);
    handler.sendMessage(message.toString());
  }

  public void sendChannelMode(String channel, String mode, String parameters) {
    StringBuilder message = new StringBuilder();
    message.append("MODE ").append(channel).append(" ").append(mode).append(" ").append(parameters);
    handler.sendMessage(message.toString());
  }

  /**
   * check the topic
   */
  public void sendTopic(String channel) {
    StringBuilder output = new StringBuilder();
    output.append("TOPIC ").append(channel);
    handler.sendMessage(output.toString());
  }

  /**
   * set a topic
   * 
   * @param channel
   * @param topic if empty, clear the topic on channel
   */
  public void sendTopic(String channel, String topic) {
    StringBuilder output = new StringBuilder();
    output.append("TOPIC ").append(channel).append(" :").append(topic);
    handler.sendMessage(output.toString());
  }



  public void sendPrivMsg(String channel, String message) {
    StringBuilder output = new StringBuilder();
    output.append("PRIVMSG ").append(channel).append(" :").append(message);
    handler.sendMessage(output.toString());
  }

  public void sendKick(String channel, String user, String message) {
    StringBuilder output = new StringBuilder();
    output.append("KICK ").append(channel).append(" ").append(user);
    if (message != null && !message.isEmpty()) {
      output.append(" :").append(message);
    }
    handler.sendMessage(output.toString());
  }

  public void sendPong(String pong) {
    StringBuilder output = new StringBuilder();
    output.append("PONG ").append(pong);
    handler.sendMessage(output.toString());
  }



  public void sendInvite(String user, String channel) {
    StringBuilder output = new StringBuilder();
    output.append("INVITE ").append(user).append(" ").append(channel);
    handler.sendMessage(output.toString());
  }

  public void sendWhoIs(String user) {
    StringBuilder output = new StringBuilder();
    output.append("WHOIS ").append(user);
    handler.sendMessage(output.toString());
  }

  public void sendNotice(String channel, String message) {
    StringBuilder output = new StringBuilder();
    output.append("NOTICE ").append(channel).append(" ").append(message);
    handler.sendMessage(output.toString());
  }

  public void sendIson(String... user) {
    StringBuilder output = new StringBuilder("ISON");
    Stream.of(user).forEach(u -> output.append(" ").append(u));
    handler.sendMessage(output.toString());
  }

  public void sendMessage(String messagen) {
    handler.sendMessage(messagen);
  }
}

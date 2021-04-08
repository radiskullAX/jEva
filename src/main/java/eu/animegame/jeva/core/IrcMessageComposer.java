package eu.animegame.jeva.core;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import java.util.stream.Stream;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.irc.UserMode;

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

  /**
   * get a list of modes set for given user
   * 
   * @param nick
   */
  public void sendMode(String nick) {
    StringBuilder message = new StringBuilder();
    message.append("MODE ").append(nick);
    handler.sendMessage(message.toString());
  }

  public void sendAddUserMode(String nick, String mode) {
    sendMode(nick, "+", mode);
  }

  public void sendAddUserMode(String nick, UserMode mode) {
    sendMode(nick, "+", mode.toString());
  }

  public void sendRemoveUserMode(String nick, String mode) {
    sendMode(nick, "-", mode);
  }

  public void sendRemoveUserMode(String nick, UserMode mode) {
    sendMode(nick, "-", mode.toString());
  }

  private void sendMode(String nick, String set, String mode) {
    StringBuilder message = new StringBuilder();
    message.append("MODE ").append(nick).append(" ").append(set).append(mode);
    handler.sendMessage(message.toString());
  }

  public void sendService(String nick, String distribution) {
    // TODO: implement sendService(), do some research before hand
  }

  public void sendQuit() {
    handler.sendMessage("QUIT");
  }

  public void sendQuit(String quitMessage) {
    StringBuilder message = new StringBuilder();
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

  public void sendJoin(String[] channels) {
    sendJoin(Arrays.stream(channels).collect(joining(",")));
  }

  public void sendJoin(String channel, String key) {
    StringBuilder message = new StringBuilder();
    message.append("JOIN ").append(channel).append(" ").append(key);
    handler.sendMessage(message.toString());
  }


  /**
   * channels with passwords should come first
   * 
   * @param channels
   * @param keys
   */
  public void sendJoin(String[] channels, String[] keys) {
    sendJoin(Arrays.stream(channels).collect(joining(",")), Arrays.stream(keys).collect(joining(",")));
  }

  public void sendPartAll() {
    handler.sendMessage("JOIN 0");
  }

  public void sendPart(String channel) {
    StringBuilder message = new StringBuilder();
    message.append("PART ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendPart(String[] channels) {
    sendPart(Arrays.stream(channels).collect(joining(",")));
  }

  public void sendPart(String channel, String partMessage) {
    StringBuilder message = new StringBuilder();
    message.append("PART ").append(channel).append(" :").append(partMessage);
    handler.sendMessage(message.toString());
  }

  public void sendPart(String[] channels, String partMessage) {
    sendPart(Arrays.stream(channels).collect(joining(",")), partMessage);
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
    StringBuilder message = new StringBuilder();
    message.append("TOPIC ").append(channel);
    handler.sendMessage(message.toString());
  }

  /**
   * set a topic
   * 
   * @param channel
   * @param topic if empty, clear the topic on channel
   */
  public void sendTopic(String channel, String topic) {
    StringBuilder message = new StringBuilder();
    message.append("TOPIC ").append(channel).append(" :").append(topic);
    handler.sendMessage(message.toString());
  }

  /**
   * list all channels + users
   */
  public void sendNames() {
    handler.sendMessage("NAMES");
  }

  public void sendNames(String channel) {
    sendNames(channel, "");
  }

  public void sendNames(String[] channels) {
    sendNames(Arrays.stream(channels).collect(joining(",")), "");
  }

  public void sendNames(String channel, String target) {
    StringBuilder message = new StringBuilder();
    message.append("NAMES ").append(channel).append(" ").append(target);
    handler.sendMessage(message.toString());
  }

  public void sendNames(String[] channels, String target) {
    sendNames(Arrays.stream(channels).collect(joining(",")), target);
  }

  /**
   * list all channels
   */
  public void sendList() {
    handler.sendMessage("LIST");
  }

  public void sendList(String channel) {
    sendList(channel, "");
  }

  public void sendList(String[] channels) {
    sendList(Arrays.stream(channels).collect(joining(",")), "");
  }

  public void sendList(String channel, String target) {
    StringBuilder message = new StringBuilder();
    message.append("LIST ").append(channel).append(" ").append(target);
    handler.sendMessage(message.toString());
  }

  public void sendList(String[] channels, String target) {
    sendList(Arrays.stream(channels).collect(joining(",")), target);
  }

  public void sendInvite(String user, String channel) {
    StringBuilder message = new StringBuilder();
    message.append("INVITE ").append(user).append(" ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendKick(String channel, String user) {
    StringBuilder message = new StringBuilder();
    message.append("KICK ").append(channel).append(" ").append(user);
    handler.sendMessage(message.toString());
  }

  public void sendKick(String[] channels, String[] users) {
    sendKick(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")));
  }

  public void sendKick(String channel, String user, String kickMessage) {
    StringBuilder message = new StringBuilder();
    message.append("KICK ").append(channel).append(" ").append(user).append(" :").append(kickMessage);
    handler.sendMessage(message.toString());
  }

  public void sendKick(String[] channels, String[] users, String message) {
    sendKick(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")), message);
  }

  /**
   * 
   * @param target a channel or user
   * @param privMessage
   */
  public void sendPrivMsg(String target, String privMessage) {
    StringBuilder message = new StringBuilder();
    message.append("PRIVMSG ").append(target).append(" :").append(privMessage);
    handler.sendMessage(message.toString());
  }

  public void sendNotice(String target, String noticeMessage) {
    StringBuilder message = new StringBuilder();
    message.append("NOTICE ").append(target).append(" :").append(noticeMessage);
    handler.sendMessage(message.toString());
  }

  public void sendMotd() {
    sendMotd("");
  }

  public void sendMotd(String server) {
    StringBuilder message = new StringBuilder();
    message.append("MOTD ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendLusers() {
    handler.sendMessage("LUSERS");
  }

  public void sendLusers(String mask) {
    sendLusers(mask, "");
  }

  public void sendLusers(String mask, String server) {
    StringBuilder message = new StringBuilder();
    message.append("LUSERS ").append(mask).append(" ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendVersion() {
    sendVersion("");
  }

  public void sendVersion(String server) {
    StringBuilder message = new StringBuilder();
    message.append("VERSION ").append(server);
    handler.sendMessage(message.toString());
  }




  public void sendPong(String pong) {
    StringBuilder output = new StringBuilder();
    output.append("PONG ").append(pong);
    handler.sendMessage(output.toString());
  }

  public void sendWhoIs(String user) {
    StringBuilder output = new StringBuilder();
    output.append("WHOIS ").append(user);
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

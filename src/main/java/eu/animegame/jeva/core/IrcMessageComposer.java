package eu.animegame.jeva.core;

import static java.util.stream.Collectors.joining;
import java.util.Arrays;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.irc.StatsQuery;
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
    var message = new StringBuilder();
    message.append("PASS ").append(password);
    handler.sendMessage(message.toString());
  }

  public void sendNick(String nick) {
    var message = new StringBuilder();
    message.append("NICK ").append(nick);
    handler.sendMessage(message.toString());
  }

  public void sendUser(String user, int mode, String realName) {
    var message = new StringBuilder();
    message.append("USER ").append(user).append(" ").append(mode).append(" :").append(realName);
    handler.sendMessage(message.toString());
  }

  public void sendOper(String name, String password) {
    var message = new StringBuilder();
    message.append("OPER ").append(name).append(" ").append(password);
    handler.sendMessage(message.toString());
  }

  /**
   * get a list of modes set for given user
   * 
   * @param nick
   */
  public void sendMode(String nick) {
    var message = new StringBuilder();
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
    var message = new StringBuilder();
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
    var message = new StringBuilder();
    message.append("QUIT").append(" :").append(quitMessage);
    handler.sendMessage(message.toString());
  }

  public void sendSQuit(String server, String comment) {
    var message = new StringBuilder();
    message.append("SQUIT ").append(server).append(" :").append(comment);
    handler.sendMessage(message.toString());
  }

  public void sendJoin(String channel) {
    var message = new StringBuilder();
    message.append("JOIN ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendJoin(String[] channels) {
    sendJoin(Arrays.stream(channels).collect(joining(",")));
  }

  public void sendJoin(String channel, String key) {
    var message = new StringBuilder();
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
    var message = new StringBuilder();
    message.append("PART ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendPart(String[] channels) {
    sendPart(Arrays.stream(channels).collect(joining(",")));
  }

  public void sendPart(String channel, String partMessage) {
    var message = new StringBuilder();
    message.append("PART ").append(channel).append(" :").append(partMessage);
    handler.sendMessage(message.toString());
  }

  public void sendPart(String[] channels, String partMessage) {
    sendPart(Arrays.stream(channels).collect(joining(",")), partMessage);
  }

  public void sendChannelMode(String channel, String mode, String parameters) {
    var message = new StringBuilder();
    message.append("MODE ").append(channel).append(" ").append(mode).append(" ").append(parameters);
    handler.sendMessage(message.toString());
  }

  /**
   * check the topic
   */
  public void sendTopic(String channel) {
    var message = new StringBuilder();
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
    var message = new StringBuilder();
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
    var message = new StringBuilder();
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
    var message = new StringBuilder();
    message.append("LIST ").append(channel).append(" ").append(target);
    handler.sendMessage(message.toString());
  }

  public void sendList(String[] channels, String target) {
    sendList(Arrays.stream(channels).collect(joining(",")), target);
  }

  public void sendInvite(String user, String channel) {
    var message = new StringBuilder();
    message.append("INVITE ").append(user).append(" ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendKick(String channel, String user) {
    var message = new StringBuilder();
    message.append("KICK ").append(channel).append(" ").append(user);
    handler.sendMessage(message.toString());
  }

  public void sendKick(String[] channels, String[] users) {
    sendKick(Arrays.stream(channels).collect(joining(",")), Arrays.stream(users).collect(joining(",")));
  }

  public void sendKick(String channel, String user, String kickMessage) {
    var message = new StringBuilder();
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
    var message = new StringBuilder();
    message.append("PRIVMSG ").append(target).append(" :").append(privMessage);
    handler.sendMessage(message.toString());
  }

  public void sendNotice(String target, String noticeMessage) {
    var message = new StringBuilder();
    message.append("NOTICE ").append(target).append(" :").append(noticeMessage);
    handler.sendMessage(message.toString());
  }

  public void sendMotd() {
    sendMotd("");
  }

  public void sendMotd(String server) {
    var message = new StringBuilder();
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
    var message = new StringBuilder();
    message.append("LUSERS ").append(mask).append(" ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendVersion() {
    sendVersion("");
  }

  public void sendVersion(String server) {
    var message = new StringBuilder();
    message.append("VERSION ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendStats(String server) {
    sendStats("", server);
  }

  public void sendStats(StatsQuery query, String server) {
    sendStats(query.toString(), server);
  }

  public void sendStats(String query, String server) {
    var message = new StringBuilder();
    message.append("STATS ").append(query).append(" ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendLinks() {
    handler.sendMessage("LINKS");
  }

  public void sendLinks(String serverMask) {
    sendLinks("", serverMask);
  }

  public void sendLinks(String server, String serverMask) {
    var message = new StringBuilder();
    message.append("LINKS ").append(server).append(" ").append(serverMask);
    handler.sendMessage(message.toString());
  }

  public void sendTime() {
    handler.sendMessage("TIME");
  }

  public void sendTime(String server) {
    var message = new StringBuilder();
    message.append("TIME ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendConnect(String server, int port) {
    sendConnect(server, port, "");
  }

  public void sendConnect(String server, int port, String remoteServer) {
    var message = new StringBuilder();
    message.append("CONNECT ").append(server).append(" ").append(port).append(" ").append(remoteServer);
    handler.sendMessage(message.toString());
  }

  public void sendTrace() {
    handler.sendMessage("TRACE");
  }

  public void sendTrace(String server) {
    var message = new StringBuilder();
    message.append("TRACE ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendAdmin() {
    handler.sendMessage("ADMIN");
  }

  public void sendAdmin(String target) {
    var message = new StringBuilder();
    message.append("ADMIN ").append(target);
    handler.sendMessage(message.toString());
  }

  public void sendInfo(String target) {
    var message = new StringBuilder();
    message.append("INFO ").append(target);
    handler.sendMessage(message.toString());
  }

  public void sendServlist(String mask) {
    sendServlist(mask, "");
  }

  public void sendServlist(String mask, String type) {
    var message = new StringBuilder();
    message.append("SERVLIST ").append(mask).append(" ").append(type);
    handler.sendMessage(message.toString());
  }

  public void sendSQuery(String serviceName, String serviceMessage) {
    var message = new StringBuilder();
    message.append("SQUERY ").append(serviceName).append(" :").append(serviceMessage);
    handler.sendMessage(message.toString());
  }

  public void sendWho() {
    sendWho("0");
  }

  public void sendWho(String target) {
    var message = new StringBuilder();
    message.append("WHO ").append(target);
    handler.sendMessage(message.toString());
  }

  public void sendWhoOperatorsOnly() {
    sendWhoOperatorsOnly("");
  }

  public void sendWhoOperatorsOnly(String target) {
    var message = new StringBuilder();
    message.append("WHO ").append(target).append(" ").append(UserMode.o);
    handler.sendMessage(message.toString());
  }

  public void sendWhoIs(String user) {
    sendWhoIs("", user);
  }

  public void sendWhoIs(String[] users) {
    sendWhoIs("", Arrays.stream(users).collect(joining(",")));
  }

  public void sendWhoIs(String server, String[] users) {
    sendWhoIs(server, Arrays.stream(users).collect(joining(",")));
  }

  public void sendWhoIs(String server, String user) {
    var message = new StringBuilder();
    message.append("WHOIS ").append(server).append(" ").append(user);
    handler.sendMessage(message.toString());
  }

  public void sendWhoWas(String nick) {
    var message = new StringBuilder();
    message.append("WHOWAS ").append(nick);
    handler.sendMessage(message.toString());
  }

  public void sendWhoWas(String nick, int count) {
    sendWhoWas(nick, count, "");
  }

  public void sendWhoWas(String nick, int count, String server) {
    var message = new StringBuilder();
    message.append("WHOWAS ").append(nick).append(" ").append(count).append(" ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendKill(String nick, String comment) {
    var message = new StringBuilder();
    message.append("KILL ").append(nick).append(" ").append(comment);
    handler.sendMessage(message.toString());
  }

  public void sendPing(String target) {
    sendPing(target, "");
  }

  public void sendPing(String target, String server) {
    var message = new StringBuilder();
    message.append("PING ").append(target).append(" ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendPong(String pong) {
    sendPong(pong, "");
  }

  public void sendPong(String pong, String server) {
    var message = new StringBuilder();
    message.append("PONG ").append(pong).append(" ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendError(String errorMessage) {
    var message = new StringBuilder();
    message.append("ERROR ").append(errorMessage);
    handler.sendMessage(message.toString());
  }

  public void sendAway() {
    handler.sendMessage("AWAY");
  }

  public void sendAway(String awayMessage) {
    var message = new StringBuilder();
    message.append("AWAY :").append(awayMessage);
    handler.sendMessage(message.toString());
  }

  public void sendRehash() {
    handler.sendMessage("REHASH");
  }

  public void sendDie() {
    handler.sendMessage("DIE");
  }

  public void sendRestart() {
    handler.sendMessage("RESTART");
  }

  public void sendSummon(String nick) {
    sendSummon(nick, "", "");
  }

  public void sendSummon(String nick, String server) {
    sendSummon(nick, server, "");
  }

  public void sendSummon(String nick, String server, String channel) {
    var message = new StringBuilder();
    message.append("SUMMON ").append(nick).append(" ").append(server).append(" ").append(channel);
    handler.sendMessage(message.toString());
  }

  public void sendUsers(String server) {
    var message = new StringBuilder();
    message.append("USERS ").append(server);
    handler.sendMessage(message.toString());
  }

  public void sendWallOps(String wallOpsMessage) {
    var message = new StringBuilder();
    message.append("WALLOPS :").append(wallOpsMessage);
    handler.sendMessage(message.toString());
  }

  public void sendUserhost(String nick) {
    var message = new StringBuilder();
    message.append("USERHOST :").append(nick);
    handler.sendMessage(message.toString());
  }

  public void sendUserhost(String[] nicks) {
    sendUserhost(Arrays.stream(nicks).collect(joining(" ")));
  }

  public void sendIson(String nick) {
    var message = new StringBuilder();
    message.append("ISON ").append(nick);
    handler.sendMessage(message.toString());
  }

  public void sendIson(String[] nicks) {
    sendIson(Arrays.stream(nicks).collect(joining(" ")));
  }

  public void sendRawMessage(String rawMessage) {
    handler.sendMessage(rawMessage);
  }
}

package eu.animegame.jeva.events;

/**
 * Categorize all kinds of messages within this enumeration
 * 
 * @author radiskull
 *
 */
public enum IrcHandlerEventType {
  PRIVMSG,
  MODE,
  KICK,
  BANN,
  PING,
  JOIN,
  NICK,
  USER,
  PASS,
  INVITE,
  WHOIS,
  QUIT,
  PART,
  NOTICE,
  ISON,
  HELP,
  COMMAND,
  UNDEFINED,
  // These are no Irc events but handler events
  STARTUP,
  CONNECTED,
  DISCONNECTED,
}

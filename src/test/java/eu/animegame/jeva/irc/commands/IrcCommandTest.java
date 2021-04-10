package eu.animegame.jeva.irc.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.irc.ModeSetting;

class IrcCommandTest {

  private static final String NICK = "Tester";

  private static final String SERVER = "TestIrcServer";

  private static final String CHANNEL = "#RunTheTests";

  private static final String PASSWORD = "Secrets";

  private static final String MESSAGE = "This is a test.";

  private static final String MASK = "#$0";

  private String expected;

  private IrcCommand command;

  @BeforeEach
  void init() {
    expected = "";
    command = null;
  }

  @Test
  void testBuildPass() {
    command = new Pass(PASSWORD);
    expected = "PASS " + PASSWORD;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildNick() {
    command = new Nick(NICK);
    expected = "NICK " + NICK;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildUser() {
    var realName = "Real Tester";
    var mode = 4;

    command = new User(NICK, mode, realName);
    expected = "USER " + NICK + " " + mode + " * :" + realName;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildOper() {
    command = new Oper(NICK, PASSWORD);
    expected = "OPER " + NICK + " " + PASSWORD;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildMode() {
    var mode = "i";

    command = new UserMode(NICK);
    expected = "MODE " + NICK;
    assertEquals(expected, command.build());

    command = new UserMode(NICK, mode, ModeSetting.ADD);
    expected = "MODE " + NICK + " +" + mode;
    assertEquals(expected, command.build());

    command = new UserMode(NICK, mode, ModeSetting.REMOVE);
    expected = "MODE " + NICK + " -" + mode;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildService() {
    var name = "TestService";
    var info = "This service is a test.";

    command = new Service(name, SERVER, info);
    expected = "SERVICE " + name + " * " + SERVER + " 0 0 :" + info;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildQuit() {
    command = new Quit();
    expected = "QUIT";
    assertEquals(expected, command.build());

    command = new Quit(MESSAGE);
    expected = "QUIT :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildSQuit() {
    command = new SQuit(SERVER, MESSAGE);
    expected = "SQUIT " + SERVER + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildJoin() {
    command = new Join();
    expected = "JOIN 0";
    assertEquals(expected, command.build());

    command = new Join(CHANNEL);
    expected = "JOIN " + CHANNEL;
    assertEquals(expected, command.build());

    command = new Join(CHANNEL, PASSWORD);
    expected = "JOIN " + CHANNEL + " " + PASSWORD;
    assertEquals(expected, command.build());

    command = new Join(new String[] {CHANNEL, CHANNEL});
    expected = "JOIN " + CHANNEL + "," + CHANNEL;
    assertEquals(expected, command.build());

    command = new Join(new String[] {CHANNEL, CHANNEL}, new String[] {PASSWORD});
    expected = "JOIN " + CHANNEL + "," + CHANNEL + " " + PASSWORD;
    assertEquals(expected, command.build());

    command = new Join(new String[] {CHANNEL, CHANNEL}, new String[] {PASSWORD, PASSWORD});
    expected = "JOIN " + CHANNEL + "," + CHANNEL + " " + PASSWORD + "," + PASSWORD;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildPart() {
    command = new Part(CHANNEL);
    expected = "PART " + CHANNEL;
    assertEquals(expected, command.build());

    command = new Part(CHANNEL, MESSAGE);
    expected = "PART " + CHANNEL + " :" + MESSAGE;
    assertEquals(expected, command.build());

    command = new Part(new String[] {CHANNEL, CHANNEL});
    expected = "PART " + CHANNEL + "," + CHANNEL;
    assertEquals(expected, command.build());

    command = new Part(new String[] {CHANNEL, CHANNEL}, MESSAGE);
    expected = "PART " + CHANNEL + "," + CHANNEL + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildChannelMode() {
    var mode = "s";
    var params = "test";

    command = new ChannelMode(CHANNEL, ModeSetting.ADD, mode);
    expected = "MODE " + CHANNEL + " +" + mode;
    assertEquals(expected, command.build());

    command = new ChannelMode(CHANNEL, ModeSetting.REMOVE, mode);
    expected = "MODE " + CHANNEL + " -" + mode;
    assertEquals(expected, command.build());

    command = new ChannelMode(CHANNEL, ModeSetting.ADD, mode, params);
    expected = "MODE " + CHANNEL + " +" + mode + " " + params;
    assertEquals(expected, command.build());

    command = new ChannelMode(CHANNEL, ModeSetting.REMOVE, mode, params);
    expected = "MODE " + CHANNEL + " -" + mode + " " + params;
    assertEquals(expected, command.build());

    command = new ChannelMode(CHANNEL, mode);
    expected = "MODE " + CHANNEL + " " + mode;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildTopic() {
    command = new Topic(CHANNEL);
    expected = "TOPIC " + CHANNEL;
    assertEquals(expected, command.build());

    command = new Topic(CHANNEL, "");
    expected = "TOPIC " + CHANNEL + " :";
    assertEquals(expected, command.build());

    command = new Topic(CHANNEL, MESSAGE);
    expected = "TOPIC " + CHANNEL + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildNames() {
    command = new Names();
    expected = "NAMES";
    assertEquals(expected, command.build());

    command = new Names(CHANNEL);
    expected = "NAMES " + CHANNEL;
    assertEquals(expected, command.build());

    command = new Names(CHANNEL, SERVER);
    expected = "NAMES " + CHANNEL + " " + SERVER;
    assertEquals(expected, command.build());

    command = new Names(new String[] {CHANNEL, CHANNEL});
    expected = "NAMES " + CHANNEL + "," + CHANNEL;
    assertEquals(expected, command.build());

    command = new Names(new String[] {CHANNEL, CHANNEL}, SERVER);
    expected = "NAMES " + CHANNEL + "," + CHANNEL + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildList() {
    command = new List();
    expected = "LIST";
    assertEquals(expected, command.build());

    command = new List(CHANNEL);
    expected = "LIST " + CHANNEL;
    assertEquals(expected, command.build());

    command = new List(CHANNEL, SERVER);
    expected = "LIST " + CHANNEL + " " + SERVER;
    assertEquals(expected, command.build());

    command = new List(new String[] {CHANNEL, CHANNEL});
    expected = "LIST " + CHANNEL + "," + CHANNEL;
    assertEquals(expected, command.build());

    command = new List(new String[] {CHANNEL, CHANNEL}, SERVER);
    expected = "LIST " + CHANNEL + "," + CHANNEL + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildInvite() {
    command = new Invite(NICK, CHANNEL);
    expected = "INVITE " + NICK + " " + CHANNEL;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildKick() {
    command = new Kick(CHANNEL, NICK);
    expected = "KICK " + CHANNEL + " " + NICK;
    assertEquals(expected, command.build());

    command = new Kick(CHANNEL, NICK, MESSAGE);
    expected = "KICK " + CHANNEL + " " + NICK + " :" + MESSAGE;
    assertEquals(expected, command.build());

    command = new Kick(new String[] {CHANNEL, CHANNEL}, new String[] {NICK, NICK});
    expected = "KICK " + CHANNEL + "," + CHANNEL + " " + NICK + "," + NICK;
    assertEquals(expected, command.build());

    command = new Kick(new String[] {CHANNEL, CHANNEL}, new String[] {NICK, NICK}, MESSAGE);
    expected = "KICK " + CHANNEL + "," + CHANNEL + " " + NICK + "," + NICK + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildPrivMsg() {
    command = new PrivMsg(NICK, MESSAGE);
    expected = "PRIVMSG " + NICK + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildNotice() {
    command = new Notice(NICK, MESSAGE);
    expected = "NOTICE " + NICK + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildMotd() {
    command = new Motd();
    expected = "MOTD";
    assertEquals(expected, command.build());

    command = new Motd(SERVER);
    expected = "MOTD " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildLusers() {
    command = new Lusers();
    expected = "LUSERS";
    assertEquals(expected, command.build());

    command = new Lusers(MASK);
    expected = "LUSERS " + MASK;
    assertEquals(expected, command.build());

    command = new Lusers(MASK, SERVER);
    expected = "LUSERS " + MASK + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildVersion() {
    command = new Version();
    expected = "VERSION";
    assertEquals(expected, command.build());

    command = new Version(SERVER);
    expected = "VERSION " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildStats() {
    var query = "m";

    command = new Stats(SERVER);
    expected = "STATS " + SERVER;
    assertEquals(expected, command.build());

    command = new Stats(query, SERVER);
    expected = "STATS " + query + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildLinks() {
    command = new Links();
    expected = "LINKS";
    assertEquals(expected, command.build());

    command = new Links(MASK);
    expected = "LINKS " + MASK;
    assertEquals(expected, command.build());

    command = new Links(SERVER, MASK);
    expected = "LINKS " + SERVER + " " + MASK;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildTime() {
    command = new Time();
    expected = "TIME";
    assertEquals(expected, command.build());

    command = new Time(SERVER);
    expected = "TIME " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildConnect() {
    var port = 6668;

    command = new Connect(SERVER, port);
    expected = "CONNECT " + SERVER + " " + port;
    assertEquals(expected, command.build());

    command = new Connect(SERVER, port, SERVER);
    expected = "CONNECT " + SERVER + " " + port + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildTrace() {
    command = new Trace();
    expected = "TRACE";
    assertEquals(expected, command.build());

    command = new Trace(SERVER);
    expected = "TRACE " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildAdmin() {
    command = new Admin();
    expected = "ADMIN";
    assertEquals(expected, command.build());

    command = new Admin(SERVER);
    expected = "ADMIN " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildInfo() {
    command = new Info(NICK);
    expected = "INFO " + NICK;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildServList() {
    var type = "bot";

    command = new ServList();
    expected = "SERVLIST";
    assertEquals(expected, command.build());

    command = new ServList(MASK);
    expected = "SERVLIST " + MASK;
    assertEquals(expected, command.build());

    command = new ServList(MASK, type);
    expected = "SERVLIST " + MASK + " " + type;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildSQuery() {
    command = new SQuery(NICK, MESSAGE);
    expected = "SQUERY " + NICK + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildWho() {
    command = new Who(false);
    expected = "WHO 0";
    assertEquals(expected, command.build());

    command = new Who(true);
    expected = "WHO 0 o";
    assertEquals(expected, command.build());

    command = new Who(MASK, false);
    expected = "WHO " + MASK;
    assertEquals(expected, command.build());

    command = new Who(MASK, true);
    expected = "WHO " + MASK + " o";
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildWhoIs() {
    command = new WhoIs(NICK);
    expected = "WHOIS " + NICK;
    assertEquals(expected, command.build());

    command = new WhoIs(NICK, SERVER);
    expected = "WHOIS " + SERVER + " " + NICK;
    assertEquals(expected, command.build());

    command = new WhoIs(new String[] {NICK, NICK});
    expected = "WHOIS " + NICK + "," + NICK;
    assertEquals(expected, command.build());

    command = new WhoIs(new String[] {NICK, NICK}, SERVER);
    expected = "WHOIS " + SERVER + " " + NICK + "," + NICK;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildWhoWas() {
    var count = 3;

    command = new WhoWas(NICK);
    expected = "WHOWAS " + NICK;
    assertEquals(expected, command.build());

    command = new WhoWas(NICK, count);
    expected = "WHOWAS " + NICK + " " + count;
    assertEquals(expected, command.build());

    command = new WhoWas(NICK, count, SERVER);
    expected = "WHOWAS " + NICK + " " + count + " " + SERVER;
    assertEquals(expected, command.build());

    command = new WhoWas(new String[] {NICK, NICK});
    expected = "WHOWAS " + NICK + "," + NICK;
    assertEquals(expected, command.build());

    command = new WhoWas(new String[] {NICK, NICK}, count);
    expected = "WHOWAS " + NICK + "," + NICK + " " + count;
    assertEquals(expected, command.build());

    command = new WhoWas(new String[] {NICK, NICK}, count, SERVER);
    expected = "WHOWAS " + NICK + "," + NICK + " " + count + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildKill() {
    command = new Kill(NICK, MESSAGE);
    expected = "KILL " + NICK + " :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildPing() {
    command = new Ping(SERVER);
    expected = "PING " + SERVER;
    assertEquals(expected, command.build());

    command = new Ping(SERVER, SERVER);
    expected = "PING " + SERVER + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildPong() {
    command = new Pong(MESSAGE);
    expected = "PONG " + MESSAGE;
    assertEquals(expected, command.build());

    command = new Pong(MESSAGE, SERVER);
    expected = "PONG " + MESSAGE + " " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildError() {
    command = new Error(MESSAGE);
    expected = "ERROR " + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildAway() {
    command = new Away();
    expected = "AWAY";
    assertEquals(expected, command.build());

    command = new Away(MESSAGE);
    expected = "AWAY :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildRehash() {
    command = new Rehash();
    expected = "REHASH";
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildDie() {
    command = new Die();
    expected = "DIE";
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildRestart() {
    command = new Restart();
    expected = "RESTART";
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildSummon() {
    command = new Summon(NICK);
    expected = "SUMMON " + NICK;
    assertEquals(expected, command.build());

    command = new Summon(NICK, SERVER);
    expected = "SUMMON " + NICK + " " + SERVER;
    assertEquals(expected, command.build());

    command = new Summon(NICK, SERVER, CHANNEL);
    expected = "SUMMON " + NICK + " " + SERVER + " " + CHANNEL;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildUsers() {
    command = new Users(SERVER);
    expected = "USERS " + SERVER;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildOperwall() {
    command = new Wallops(MESSAGE);
    expected = "WALLOPS :" + MESSAGE;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildUserhost() {
    command = new UserHost(NICK);
    expected = "USERHOST " + NICK;
    assertEquals(expected, command.build());

    command = new UserHost(new String[] {NICK, NICK});
    expected = "USERHOST " + NICK + " " + NICK;
    assertEquals(expected, command.build());
  }

  @Test
  void testBuildIson() {
    command = new Ison(NICK);
    expected = "ISON " + NICK;
    assertEquals(expected, command.build());

    command = new Ison(new String[] {NICK, NICK});
    expected = "ISON " + NICK + " " + NICK;
    assertEquals(expected, command.build());
  }
}

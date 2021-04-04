package eu.animegame.jeva;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.animegame.jeva.utils.RegexHelper;

/**
 *
 * @author radiskull
 */
public class ParseTest {

	public ParseTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	// @Test
	public void parsePingEvent() {
		System.out.println("--parsePingEvent--");
		// PING :stockholm.se.quakenet.org
		// PING :2482250717
		String command = "PING :stockholm.se.quakenet.org";
		String pong = command.substring(command.indexOf(":") + 1);
		System.out.println(pong);
		assertEquals(0, pong.compareTo("stockholm.se.quakenet.org"));

		command = "PING :2482250717";
		pong = command.substring(command.indexOf(":") + 1);
		System.out.println(pong);
		assertEquals(0, pong.compareTo("2482250717"));
	}

	@Test
	public void parseSenderHostTestRadi() {
		System.out.println("--parseSenderHostTestRadi--");
		// :stockholm.se.quakenet.org NOTICE botname :on 2 ca 2(4) ft 20(20) tr
		// :botname!~botname@195.37.166.242 MODE botname +i
		// :Q!TheQBot@CServe.quakenet.org MODE #animegame +v botname
		// !
		final Pattern EVENTPARSER = Pattern.compile("^:([^\\!\\s]+)[\\s\\!]([^\\s]+)\\s.*");

		String command = ":botname!~botname@195.37.166.242 MODE botname +i";
		String[] result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("botname"));
		assertEquals(0, result[1].compareTo("~botname@195.37.166.242"));

		command = ":Q!TheQBot@CServe.quakenet.org MODE #animegame +v botname";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("Q"));
		assertEquals(0, result[1].compareTo("TheQBot@CServe.quakenet.org"));

		command = ":stockholm.se.quakenet.org NOTICE botname :on 2 ca 2(4) ft 20(20) tr";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("stockholm.se.quakenet.org"));
	}

	@Test
	public void parseSenderHostTestHecht() {
		System.out.println("--parseSenderHostTestHecht--");
		// :stockholm.se.quakenet.org NOTICE botname :on 2 ca 2(4) ft 20(20) tr
		// :botname!~botname@195.37.166.242 MODE botname +i
		// :Q!TheQBot@CServe.quakenet.org MODE #animegame +v botname
		// !
		final Pattern EVENTPARSER = Pattern.compile("^:(?:([^\\!\\s]+)\\!)?([^\\s]+)\\s.*");

		String command = ":botname!~botname@195.37.166.242 MODE botname +i";
		String[] result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("botname"));
		assertEquals(0, result[1].compareTo("~botname@195.37.166.242"));

		command = ":Q!TheQBot@CServe.quakenet.org MODE #animegame +v botname";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("Q"));
		assertEquals(0, result[1].compareTo("TheQBot@CServe.quakenet.org"));

		command = ":stockholm.se.quakenet.org NOTICE botname :on 2 ca 2(4) ft 20(20) tr";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[1].compareTo("stockholm.se.quakenet.org"));
	}

	@Test
	public void parseIrcEventTest() {
		System.out.println("--parseIrcEventTest--");
		// :stockholm.se.quakenet.org NOTICE botname :on 2 ca 2(4) ft 20(20) tr
		// :botname!~botname@195.37.166.242 JOIN #animegame
		// :botname!~botname@195.37.166.242 MODE botname +i
		// :radiskull!~chatzilla@radiskull.users.quakenet.org INVITE botname
		// #ag_entwickler
		// :radiskull!~chatzilla@radiskull.users.quakenet.org KICK #warofmadness botname
		// :radiskull
		// :radiskull!~chatzilla@radiskull.users.quakenet.org PRIVMSG #warofmadness
		// :blubb
		// :radiskull!~chatzilla@radiskull.users.quakenet.org QUIT :Read error: EOF from
		// client
		// :radi^ai^otgc!~theradisk@radiskull.users.quakenet.org NICK :radi^bam
		// :PING :331062545

		// nur zur sicherheit "^:[^\\s]+\\s+[^\\s]+\\s((#\\w+)\\s*(.*)|\\w+.*|:.*)"
		final Pattern EVENTPARSER = Pattern.compile("^:(?:[^\\s]+\\s){2}(([\\w\\p{Punct}]+)\\s*(.*))");

		System.out.println("JOIN-Command");
		String command = ":botname!~botname@195.37.166.242 JOIN #animegame";
		String[] result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("#animegame"));

		System.out.println("QUIT-Command");
		command = ":radiskull!~chatzilla@radiskull.users.quakenet.org QUIT :Read error: EOF from client";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo(":Read error: EOF from client"));

		System.out.println("NICK-Command");
		command = ":radi^ai^otgc!~theradisk@radiskull.users.quakenet.org NICK :radi^bam";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo(":radi^bam"));

		System.out.println("MODE-Command Server");
		command = ":botname!~botname@195.37.166.242 MODE botname +i";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("botname +i"));
		// assertEquals(null, result[1]);

		System.out.println("MODE-Command Channel");
		command = ":Q!TheQBot@CServe.quakenet.org MODE #animegame +v botname";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[1].compareTo("#animegame"));
		assertEquals(0, result[2].compareTo("+v botname"));

		System.out.println("INVITE-Command");
		command = ":radiskull!~chatzilla@radiskull.users.quakenet.org INVITE botname #ag_hattrick";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[0].compareTo("botname #ag_hattrick"));
		assertEquals(0, result[2].compareTo("#ag_hattrick"));

		System.out.println("KICK-Command");
		command = ":radiskull!~chatzilla@radiskull.users.quakenet.org KICK #radi-o botname :radiskull";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[1].compareTo("#radi-o"));
		assertEquals(0, result[2].compareTo("botname :radiskull"));

		System.out.println("MESSAGE-Command");
		command = ":radiskull!~chatzilla@radiskull.users.quakenet.org PRIVMSG #warofmadness :blubb hier geht die party ab! :D";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[1].compareTo("#warofmadness"));
		assertEquals(0, result[2].compareTo(":blubb hier geht die party ab! :D"));

		System.out.println("NOTICE-Command");
		command = ":stockholm.se.quakenet.org NOTICE botname :on 2 ca 2(4) ft 20(20) tr";
		result = RegexHelper.parseStringGroups(EVENTPARSER, command);
		show(result);
		assertEquals(0, result[2].compareTo(":on 2 ca 2(4) ft 20(20) tr"));
	}

	@Test
	public void parseServerCommands() {
		System.out.println("--parseServerCommands--");
		// :stockholm.se.quakenet.org 001 botname :Welcome to the QuakeNet IRC Network,
		// botname
		// :stockholm.se.quakenet.org 004 botname stockholm.se.quakenet.org
		// u2.10.12.10+snircd(1.3.4a) dioswkgxRXInP biklmnopstvrDcCNuMT bklov
		// :stockholm.se.quakenet.org 253 botname 3 :unknown connection(s)
		// :stockholm.se.quakenet.org 254 botname 47168 :channels formed
		// :stockholm.se.quakenet.org 375 botname :- stockholm.se.quakenet.org Message
		// of the Day -
		// :stockholm.se.quakenet.org 372 botname :- 2008-4-13 23:58
		// :stockholm.se.quakenet.org 376 botname :End of /MOTD command.
		// :stockholm.se.quakenet.org 221 botname +i
		// :stockholm.se.quakenet.org 332 radi2 #warofmadness :This is an epic Topic!:
		// test ^_^
		// :stockholm.se.quakenet.org 333 radi2 #warofmadness Q 1331277643
		// :stockholm.se.quakenet.org 353 radi2 @ #warofmadness :radi2 @radi^ai^otgc
		// LazyGenius1 @Q
		// :stockholm.se.quakenet.org 366 radi2 #warofmadness :End of /NAMES list.

		final Pattern IRC_COMMAND = Pattern.compile("^:[^\\s]+\\s(\\d{3})\\s[^\\s]+\\s(.*)");

		System.out.println("001-Command");
		String command = ":stockholm.se.quakenet.org 001 botname :Welcome to the QuakeNet IRC Network, botname";
		String[] result = RegexHelper.parseStringGroups(IRC_COMMAND, command);
		show(result);
		assertEquals(0, result[0].compareTo("001"));
		// assertEquals(0, result[1].compareTo(":Welcome to the QuakeNet IRC Network,
		// botname"));

		System.out.println("004-Command");
		command = ":stockholm.se.quakenet.org 004 botname stockholm.se.quakenet.org u2.10.12.10+snircd(1.3.4a) dioswkgxRXInP biklmnopstvrDcCNuMT bklov";
		result = RegexHelper.parseStringGroups(IRC_COMMAND, command);
		show(result);
		assertEquals(0, result[0].compareTo("004"));
		assertEquals(0, result[1].compareTo(
				"stockholm.se.quakenet.org u2.10.12.10+snircd(1.3.4a) dioswkgxRXInP biklmnopstvrDcCNuMT bklov"));

		System.out.println("332-Command");
		command = ":stockholm.se.quakenet.org 332 radi2 #warofmadness :This is an epic Topic!: test ^_^";
		result = RegexHelper.parseStringGroups(IRC_COMMAND, command);
		show(result);
		assertEquals(0, result[0].compareTo("332"));
		assertEquals(0, result[1].compareTo("#warofmadness :This is an epic Topic!: test ^_^"));

		System.out.println("353-Command");
		command = ":stockholm.se.quakenet.org 353 radi2 @ #warofmadness :radi2 @radi^ai^otgc LazyGenius1 @Q";
		result = RegexHelper.parseStringGroups(IRC_COMMAND, command);
		show(result);
		assertEquals(0, result[0].compareTo("353"));
		assertEquals(0, result[1].compareTo("@ #warofmadness :radi2 @radi^ai^otgc LazyGenius1 @Q"));
	}

	@Test
	public void parseWhoIsTest() {
		// :stockholm.se.quakenet.org 311 botname radi ~chatzilla
		// radiskull.users.quakenet.org * :overpowering soul radiskull
		// :stockholm.se.quakenet.org 319 botname radi :@#radi-o +#animegame
		// :stockholm.se.quakenet.org 312 botname radi *.quakenet.org :QuakeNet IRC
		// Server
		// :stockholm.se.quakenet.org 330 botname radi radiskull :is authed as
		// :stockholm.se.quakenet.org 318 botname radi :End of /WHOIS list.
		final Pattern IRC_COMMAND = Pattern.compile("[^\\s]+\\s([^\\s]+).*");

		System.out.println("330-Command");
		String command = "radi^ai^otgc radiskull :is authed as";
		String[] result = RegexHelper.parseStringGroups(IRC_COMMAND, command);
		show(result);
		assertEquals(0, result[0].compareTo("radiskull"));
	}

	@Test
	public void testHelpParse() {
		Pattern COMMANDS = Pattern.compile("^:@help\\s+-([acp])\\s*(.*)");

		System.out.println("testHelpParse");
		String command = ":@help -c @help";
		boolean result = RegexHelper.matches(COMMANDS, command);
		assertEquals(true, result);

		command = ":@help   -c help";
		result = RegexHelper.matches(COMMANDS, command);
		assertEquals(true, result);
		String[] parse = RegexHelper.parseStringGroups(COMMANDS, command);
		show(parse);

		command = ":@help -c  help";
		result = RegexHelper.matches(COMMANDS, command);
		assertEquals(true, result);

		command = ":@help -a";
		result = RegexHelper.matches(COMMANDS, command);
		assertEquals(true, result);
		parse = RegexHelper.parseStringGroups(COMMANDS, command);
		show(parse);

		command = ":@help -p HelpInfoCallback";
		result = RegexHelper.matches(COMMANDS, command);
		assertEquals(true, result);
		parse = RegexHelper.parseStringGroups(COMMANDS, command);
		show(parse);
	}

	private void show(String[] result) {
		StringBuilder sb = new StringBuilder("Gruppe(n): ");
		for (int i = 0; i < result.length; i++) {
			sb.append(i).append(") \"").append(result[i]).append("\" ");
		}
		System.out.println(sb.toString());
	}
}

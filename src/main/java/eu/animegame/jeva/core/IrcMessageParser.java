package eu.animegame.jeva.core;

import java.io.IOException;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.AbstractIrcHandler;
import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.utils.RegexHelper;

/**
 *
 * @author radiskull
 */
class IrcMessageParser {

	protected static Logger LOG = LoggerFactory.getLogger(AbstractIrcHandler.class);
	protected static final Pattern CHANNEL_MESSAGE_PARSER = Pattern
			.compile("^:(?:[^\\s]+\\s){2}(([\\w\\p{Punct}]+)\\s*(.*))");
	protected static final Pattern SENDER_HOST_PARSER = Pattern.compile("^:(?:([^\\!\\s]+)\\!)?([^\\s]+)\\s.*");
	protected static final Pattern IRC_COMMAND_PARSER = Pattern.compile("^:[^\\s]+\\s(\\d{3})\\s[^\\s]+\\s(.*)");
	protected static final Pattern IRC_COMMAND_FINDER = Pattern.compile("^:[^\\s]+\\s(\\d{3})\\s.+");
	protected static final int PARSING_ERROR = -1;

	public static IrcHandlerEvent parseInputMessage(String input, AbstractIrcHandler handler) throws IOException {
		if (input == null) {
			throw new IOException("Null input as message.");
			// return new IrcEvent(IrcMessageType.DISCONNECTED, "", "", "", "",
			// PARSING_ERROR, "", ircHandler);
		}

		String channel = "";
		String sender = "";
		String host = "";
		String message = "";
		int code = 0;

		IrcHandlerEventType type = parseIrcMessageType(input);
		// parse the input for an IRCEvent
		String[] inputMessage;
		if (type == IrcHandlerEventType.COMMAND) {
			inputMessage = RegexHelper.parseStringGroups(IRC_COMMAND_PARSER, input);
		} else {
			inputMessage = RegexHelper.parseStringGroups(CHANNEL_MESSAGE_PARSER, input);
		}
		String[] hostname = RegexHelper.parseStringGroups(SENDER_HOST_PARSER, input);

		// TODO: Fehlerhafter code .. da parseStringGroups ein array mit nulls
		// zurückgegeben hat, ist das hier
		// nie aufgefallen ;) .. sollten wir beheben
		if (hostname.length > 0) {
			if (hostname[0] != null) {
				sender = hostname[0];
				host = hostname[1];
			} else {
				sender = host = hostname[1];
			}
		}

		switch (type) {
		case MODE:
			if (inputMessage[1].startsWith("#")) {
				channel = inputMessage[1];
				message = inputMessage[2];
			} else {
				channel = "";
				message = inputMessage[0];
			}
			break;
		case QUIT:
			// same as invite
		case NICK:
			channel = "";
			message = inputMessage[0];
			break;
		case JOIN:
			channel = inputMessage[0];
			message = "";
			break;
		case KICK:
			channel = inputMessage[1];
			message = inputMessage[2];
			break;
		case PRIVMSG:
			channel = inputMessage[1];
			message = inputMessage[2].substring(inputMessage[2].indexOf(":"), inputMessage[2].length());
			break;
		case COMMAND:
			try {
				code = Integer.parseInt(inputMessage[0]);
			} catch (NumberFormatException nfe) {
				LOG.warn("Could not parse Command-Code!");
				nfe.printStackTrace();
			}
			message = inputMessage[1];
			break;
		case INVITE:
			channel = inputMessage[2];
			message = "";
			break;
		case NOTICE:
			channel = "";
			if (inputMessage.length > 1) {
				message = inputMessage[2];
			} else {
				message = input.substring(input.indexOf(":"));
			}
			break;
		case PING:
			// same as invite
		case UNDEFINED:
			channel = "";
			message = input;
			break;
		}

		if (channel.equals(handler.getNick())) {
			channel = sender;
		}
		return new IrcHandlerEvent(type, sender, host, channel, message, code, input, handler);
	}

	private static IrcHandlerEventType parseIrcMessageType(String text) {
		// TODO: NETSPLIT fehlt noch
		// TODO: AUTH sollte wahrscheinlich auch mit
		// Beispiel: NOTICE AUTH :*** Looking up your hostname
		if (text.contains(" PRIVMSG ")) {
			return IrcHandlerEventType.PRIVMSG;
		} else if (text.contains(" MODE ")) {
			return IrcHandlerEventType.MODE;
		} else if (text.startsWith("PING")) {
			return IrcHandlerEventType.PING;
		} else if (text.contains(" KICK ")) {
			return IrcHandlerEventType.KICK;
		} else if (text.contains(" JOIN ")) {
			return IrcHandlerEventType.JOIN;
		} else if (text.contains(" PART ")) {
			return IrcHandlerEventType.PART;
		} else if (text.contains(" INVITE ")) {
			return IrcHandlerEventType.INVITE;
		} else if (text.contains(" NICK ")) {
			return IrcHandlerEventType.NICK;
		} else if (text.contains("\\s?NOTICE ")) {
			return IrcHandlerEventType.NOTICE;
		} else if (text.contains(" QUIT ")) {
			return IrcHandlerEventType.QUIT;
		} else if (IRC_COMMAND_FINDER.matcher(text).matches()) {
			return IrcHandlerEventType.COMMAND;
		}
		return IrcHandlerEventType.UNDEFINED;
	}

}

package eu.animegame.jeva.plugins;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;
import eu.animegame.jeva.utils.RegexHelper;
import eu.animegame.jeva.utils.StringHelper;

/**
 *
 * @author radiskull
 */
public class BotControllPlugin implements IrcEventCallback, IrcHandlerPlugin {

	protected static Logger LOG = LoggerFactory.getLogger(BotControllPlugin.class);
	private static final Pattern COMMAND_PATTERN = Pattern.compile("^:@(join|quit|leave|say|disconnect).*");
	private static final Pattern SAY_PARSE_PATTERN = Pattern.compile("^:@say\\s(#[\\p{Punct}\\w]+)\\s(.+)");
	protected String quitMessage;
	protected String joinMessage;
	protected String leaveMessage;

	public BotControllPlugin() {
		this("", "", "");
	}

	public BotControllPlugin(String quitMessage, String joinMessage, String leaveMessage) {
		this.quitMessage = quitMessage;
		this.joinMessage = joinMessage;
		this.leaveMessage = leaveMessage;
	}

	@Override
	public void callback(IrcHandlerEvent ie) {
		String command = RegexHelper.parseString(COMMAND_PATTERN, ie.getMessage());
		if (!command.isEmpty()) {
			LOG.trace("Found matching command \"{}\" continue parsing", command);
			parseInput(command, ie);
		}
	}

	public void parseInput(String command, IrcHandlerEvent ie) {
		switch (command) {
		case "leave": {
			ie.getHandler().sendPart(ie.getChannel(), leaveMessage);
			LOG.info("leaving channel {} on command of user {}", ie.getChannel(), ie.getSender());
		}
			break;
		case "quit": {
			LOG.info("quit irc on command of user {}", ie.getSender());
			ie.getHandler().sendQuit(quitMessage);
		}
			break;
		case "join": {
			String channel = StringHelper.split(ie.getMessage(), " ")[1];
			ie.getHandler().sendJoin(channel, joinMessage);
			LOG.info("joining channel {} on command of user {}", channel, ie.getSender());
		}
			break;
		case "disconnect": {
			LOG.info("recieved hardkill command from user {}", ie.getSender());
			ie.getHandler().disconnect();
		}
			break;
		case "say": {
			String[] data = RegexHelper.parseStringGroups(SAY_PARSE_PATTERN, ie.getMessage());
			if (data.length > 1) {
				String channel = data[0];
				String message = data[1];
				ie.getHandler().sendPrivMsg(channel, message);
				LOG.info("user {} tells me to say the following message \"{}\" in the channel {}", ie.getSender(),
						message, channel);
			} else {
				ie.getHandler().sendPrivMsg(ie.getSender(), "incorrect Input!");
			}
		}
			break;
		}
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.PRIVMSG, this);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.PRIVMSG, this);
	}
}

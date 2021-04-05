package eu.animegame.jeva;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.Connection;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public abstract class AbstractIrcHandler implements IrcHandler {

	public static final String PROP_NICK = "nickname";
	public static final String PROP_REALNAME = "realname";
	public static final String PROP_ADDRESS = "adresse";
	public static final String PROP_PORT = "port";
	public static final String PROP_PASSWORD = "password";
	public static final String PROP_MODE = "mode";
	protected Map<IrcHandlerEventType, Set<IrcEventCallback>> callbackRegistry;
	protected List<IrcHandlerPlugin> plugins;
	protected Connection connection;
	protected Properties connectionProps;
	protected static Logger LOG = LoggerFactory.getLogger(AbstractIrcHandler.class);

	public AbstractIrcHandler(Connection connection) {
		this(connection, new Properties());
	}

	public AbstractIrcHandler(Connection connection, Properties props) {
		this.connection = connection;
		this.connectionProps = props;
		this.callbackRegistry = new HashMap<>();
		this.plugins = new ArrayList<>();
	}

	@Override
	public String getNick() {
		return connectionProps.getProperty(PROP_NICK, "IrcBot");
	}

	@Override
	public void setNick(String name) {
		if (name != null && !name.isEmpty()) {
			connectionProps.setProperty(PROP_NICK, name);
		}
	}

	@Override
	public String getRealName() {
		return connectionProps.getProperty(PROP_REALNAME, "");
	}

	@Override
	public void setRealName(String name) {
		if (name != null) {
			connectionProps.setProperty(PROP_REALNAME, name);
		}
	}

	@Override
	public String getAdresse() {
		return connectionProps.getProperty(PROP_ADDRESS, "");
	}

	@Override
	public void setAddress(String adresse) {
		if (adresse != null && !adresse.isEmpty()) {
			connectionProps.setProperty(PROP_ADDRESS, adresse);
		}
	}

	@Override
	public int getPort() {
		return Integer.parseInt(connectionProps.getProperty(PROP_PORT, "6667"));
	}

	@Override
	public void setPort(int port) {
		if (port > 0) {
			connectionProps.setProperty(PROP_PORT, String.valueOf(port));
		}
	}

	@Override
	public String getPassword() {
		return connectionProps.getProperty(PROP_PASSWORD, "");
	}

	@Override
	public void setPassword(String password) {
		if (password != null) {
			connectionProps.setProperty(PROP_PASSWORD, password);
		}
	}

	@Override
	public int getMode() {
		return Integer.parseInt(connectionProps.getProperty(PROP_MODE, "0"));
	}

	@Override
	public void setMode(int mode) {
		if (mode > -1) {
			connectionProps.setProperty(PROP_MODE, String.valueOf(mode));
		}
	}

	@Override
	public boolean addIrcHandlerPlugin(IrcHandlerPlugin plugin) {
		if (!plugins.contains(plugin)) {
			return plugins.add(plugin);
		}
		return false;
	}

	@Override
	public boolean removeIrcHandlerPlugin(IrcHandlerPlugin plugin) {
		return plugins.remove(plugin);
	}

	@Override
	public void addIrcEventCallback(IrcHandlerEventType type, IrcEventCallback callback) {
		if (callbackRegistry.containsKey(type)) {
			Set<IrcEventCallback> set = callbackRegistry.get(type);
			if (!set.contains(callback)) {
				set.add(callback);
			}
		} else {
			Set<IrcEventCallback> set = new HashSet<>();
			set.add(callback);
			callbackRegistry.put(type, set);
		}
	}

	@Override
	public void removeIrcEventCallback(IrcHandlerEventType type, IrcEventCallback callback) {
		if (callbackRegistry.containsKey(type)) {
			Set<IrcEventCallback> set = callbackRegistry.get(type);
			set.remove(callback);
		}
	}

	@Override
	public void sendMessage(String message) {
		if (!message.isEmpty()) {
			connection.write(message);
		}
	}

	@Override
	public IrcHandlerEvent parseMessage(String message) {
		try {
			return MessageParserHelper.parseInputMessage(message, this);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return new IrcHandlerEvent(IrcHandlerEventType.UNDEFINED, "", "", "", "", 0, "", this);
	}

	public void fireIrcHandlerEvent(IrcHandlerEvent ie) {
		Set<IrcEventCallback> set = callbackRegistry.get(ie.getType());
		if (set != null) {
			LOG.debug(ie.toString());
			set.stream().forEach(plugin -> fireIrcEvent(plugin, ie));
		}
	}

	private void fireIrcEvent(IrcEventCallback plugin, IrcHandlerEvent ie) {
		try {
			plugin.callback(ie);
		} catch (Exception e) {
			LOG.warn("An exception in Plugin {} occured with the message {}!", plugin.getClass().getSimpleName(),
					e.getMessage());
			LOG.warn("{}", e.fillInStackTrace());
		}
	}

	public abstract void start();

	public abstract void stop();

	public boolean connect() throws Exception {
		// TODO: handle exceptions when we can't connect to any server or other things
		// pop up
		connection.setUrl(getAdresse());
		connection.setPort(getPort());
		boolean connected = connection.connect();

		if (connected) {
			fireIrcHandlerEvent(new IrcHandlerEvent(IrcHandlerEventType.STARTUP, "", "", "", "", 0, "", this));
			return true;
		} else {
			return false;
		}
	}

	public void disconnect() {
		try {
			connection.disconnect();
		} catch (Exception e) {
			LOG.warn("Exception while stopping! {}", e.getMessage());
		}
	}

	public void sendPrivMsg(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("PRIVMSG ").append(channel).append(" :").append(message);
		sendMessage(output.toString());
	}

	public void sendMode(String user, String modes) {
		StringBuilder output = new StringBuilder();
		output.append("MODE ").append(user).append(" ").append(modes);
		sendMessage(output.toString());
	}

	public void sendKick(String channel, String user, String message) {
		StringBuilder output = new StringBuilder();
		output.append("KICK ").append(channel).append(" ").append(user);
		if (message != null && !message.isEmpty()) {
			output.append(" :").append(message);
		}
		sendMessage(output.toString());
	}

	public void sendPong(String pong) {
		StringBuilder output = new StringBuilder();
		output.append("PONG ").append(pong);
		sendMessage(output.toString());
	}

	public void sendJoin(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("JOIN ").append(channel);
		if (message != null && !message.isEmpty()) {
			output.append(" ").append(message);
		}
		sendMessage(output.toString());
	}

	public void sendNick(String nick) {
		StringBuilder output = new StringBuilder();
		if (nick != null && !nick.isEmpty()) {
			output.append("NICK ").append(nick);
		}
		sendMessage(output.toString());
	}

	public void sendUser(String user, int mode, String realName) {
		StringBuilder output = new StringBuilder();
		output.append("USER ").append(user).append(" ").append(mode).append(" * ").append(realName);
		sendMessage(output.toString());
	}

	public void sendPass(String password) {
		StringBuilder output = new StringBuilder();
		output.append("PASS ").append(password);
		sendMessage(output.toString());
	}

	public void sendInvite(String user, String channel) {
		StringBuilder output = new StringBuilder();
		output.append("INVITE ").append(user).append(" ").append(channel);
		sendMessage(output.toString());
	}

	public void sendWhoIs(String user) {
		StringBuilder output = new StringBuilder();
		output.append("WHOIS ").append(user);
		sendMessage(output.toString());
	}

	public void sendQuit(String message) {
		StringBuilder output = new StringBuilder();
		output.append("QUIT");
		if (message != null && !message.isEmpty()) {
			output.append(" :").append(message);
		}
		sendMessage(output.toString());
		fireIrcHandlerEvent(new IrcHandlerEvent(IrcHandlerEventType.QUIT, "", "", "", "", 0, message, this));
	}

	public void sendPart(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("PART ").append(channel);
		if (message != null && !message.isEmpty()) {
			output.append(" :").append(message);
		}
		sendMessage(output.toString());
	}

	public void sendNotice(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("NOTICE ").append(channel).append(" ").append(message);
		sendMessage(output.toString());
	}

	public void sendIson(String... user) {
		StringBuilder output = new StringBuilder("ISON");
		Stream.of(user).forEach(u -> output.append(" ").append(u));
		sendMessage(output.toString());
	}
}

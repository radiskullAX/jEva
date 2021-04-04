package eu.animegame.jeva;

import java.util.stream.Stream;

import eu.animegame.jeva.interfaces.IrcHandler;

/**
 *
 * @author radiskull
 */
public class IrcMessagesSender {

	protected IrcHandler handler;

	public IrcMessagesSender(IrcHandler handler) {
		this.handler = handler;
	}

	public void sendPrivMsg(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("PRIVMSG ").append(channel).append(" :").append(message);
		handler.sendMessage(output.toString());
	}

	public void sendMode(String user, String modes) {
		StringBuilder output = new StringBuilder();
		output.append("MODE ").append(user).append(" ").append(modes);
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

	public void sendJoin(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("JOIN ").append(channel);
		if (message != null && !message.isEmpty()) {
			output.append(" ").append(message);
		}
		handler.sendMessage(output.toString());
	}

	public void sendNick(String nick) {
		StringBuilder output = new StringBuilder();
		if (nick != null && !nick.isEmpty()) {
			output.append("NICK ").append(nick);
		}
		handler.sendMessage(output.toString());
	}

	public void sendUser(String user, int mode, String realName) {
		StringBuilder output = new StringBuilder();
		output.append("USER ").append(user).append(" ").append(mode).append(" ").append(realName);
		handler.sendMessage(output.toString());
	}

	public void sendPass(String password) {
		StringBuilder output = new StringBuilder();
		output.append("PASS ").append(password);
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

	public void sendQuit(String message) {
		StringBuilder output = new StringBuilder();
		output.append("QUIT");
		if (message != null && !message.isEmpty()) {
			output.append(" :").append(message);
		}
		handler.sendMessage(output.toString());
	}

	public void sendPart(String channel, String message) {
		StringBuilder output = new StringBuilder();
		output.append("PART ").append(channel);
		if (message != null && !message.isEmpty()) {
			output.append(" :").append(message);
		}
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

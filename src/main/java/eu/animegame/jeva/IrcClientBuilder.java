package eu.animegame.jeva;

import java.util.Properties;

import eu.animegame.jeva.interfaces.Connection;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class IrcClientBuilder {

	private QuakenetHandler handler;

	private IrcClientBuilder() {
		handler = new QuakenetHandler(new SocketConnection(), new Properties());
	}

	private IrcClientBuilder(Connection connection) {
		handler = new QuakenetHandler(connection, new Properties());
	}

	public static IrcClientBuilder create() {
		return new IrcClientBuilder();
	}

	public static IrcClientBuilder create(Connection connection) {
		return new IrcClientBuilder(connection);
	}

	public IrcClientBuilder withNick(String name) {
		handler.setNick(name);
		return this;
	}

	public IrcClientBuilder withPort(int port) {
		handler.setPort(port);
		return this;
	}

	public IrcClientBuilder withAddress(String address) {
		handler.setAddress(address);
		return this;
	}

	public IrcClientBuilder withMode(int mode) {
		handler.setMode(mode);
		return this;
	}

	public IrcClientBuilder withRealName(String name) {
		handler.setRealName(name);
		return this;
	}

	public IrcClientBuilder withPassword(String password) {
		handler.setPassword(password);
		return this;
	}

	public IrcClientBuilder withPlugin(IrcHandlerPlugin plugin) {
		handler.addIrcHandlerPlugin(plugin);
		return this;
	}

	public QuakenetHandler build() {
		return handler;
	}
}

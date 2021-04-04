package eu.animegame.jeva;

import java.util.Properties;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.Connection;
import eu.animegame.jeva.plugins.AuthenticationPlugin;
import eu.animegame.jeva.plugins.CloseConnectionPlugin;
import eu.animegame.jeva.plugins.ConnectPlugin;
import eu.animegame.jeva.plugins.NickChangePlugin;
import eu.animegame.jeva.plugins.PingPlugin;

/**
 *
 * @author radiskull
 */
public class QuakenetHandler extends AbstractIrcHandler {

	private ConnectPlugin connectPlugin = new ConnectPlugin();
	private PingPlugin pingPlugin = new PingPlugin();
	private NickChangePlugin nickChangePlugin = new NickChangePlugin();
	private AuthenticationPlugin authenticationPlugin = new AuthenticationPlugin();
	private CloseConnectionPlugin closeConnectionPlugin = new CloseConnectionPlugin();

	public QuakenetHandler(Connection connection, Properties props) {
		super(connection, props);
	}

	@Override
	public void start() {
		LOG.info("Starting up, please wait a second");
		try {
			registerAllPlugins();
			connect();
			waitForInput();
		} catch (Exception e) {
			LOG.warn("Could not start because of an Exception. Reason: {}", e.getMessage());
		}
	}

	@Override
	public void stop() {
		sendQuit("Closing down Bot.");
		disconnect();
		unregisterAllPlugins();
	}

	private void waitForInput() {
		while (!connection.isClosed()) {
			String input;
			try {
				input = (String) connection.read();
				IrcHandlerEvent ie = parseMessage(input);
				fireIrcHandlerEvent(ie);
			} catch (Exception e) {
				LOG.error("Something went wrong while reading input! Closing connection.");
				e.printStackTrace();
				disconnect();
			}
		}
		fireIrcHandlerEvent(new IrcHandlerEvent(IrcHandlerEventType.DISCONNECTED, "", "", "", "", 0, "", this));
	}

	private void registerAllPlugins() {
		LOG.debug("Initiliasing all Plugins");
		// TODO: this has to be moved to the constructor
		registerPlugins();
		plugins.stream()
				.peek(plugin -> LOG.debug("register all events of plugin {}", plugin.getClass().getSimpleName()))
				.forEach(plugin -> plugin.registerCallbackEvents(this));
	}

	private void registerPlugins() {
		addIrcHandlerPlugin(connectPlugin);
		addIrcHandlerPlugin(closeConnectionPlugin);
		addIrcHandlerPlugin(nickChangePlugin);
		addIrcHandlerPlugin(pingPlugin);
		addIrcHandlerPlugin(authenticationPlugin);
	}

	private void unregisterAllPlugins() {
		plugins.stream()
				.peek(plugin -> LOG.debug("unregister all events of plugin {}", plugin.getClass().getSimpleName()))
				.forEach(plugin -> plugin.unregisterCallbackEvents(this));
	}
}

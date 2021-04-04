package eu.animegame.jeva.plugins;

import org.junit.platform.commons.logging.LoggerFactory;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class ReconnectPlugin implements IrcHandlerPlugin {

	private final int RECONNECT_INTERVALL;
	private final int MAX_TRIES;
	private static Logger LOG = LoggerFactory.getLogger(ReconnectPlugin.class);
	private QuitCallback quitCallback = new QuitCallback();
	private ConnectCallback connectCallback = new ConnectCallback();
	private DisconnectCallback disconnectCallback = new DisconnectCallback();
	private boolean isClosingDown = false;
	private boolean isConnected = false;
	private int counter = 0;

	public ReconnectPlugin() {
		this(10, 15000);
	}

	public ReconnectPlugin(int maxTries, int reconnectIntervall) {
		MAX_TRIES = maxTries > 0 ? maxTries : Integer.MAX_VALUE;
		RECONNECT_INTERVALL = reconnectIntervall;
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.QUIT, quitCallback);
		handler.addIrcEventCallback(IrcHandlerEventType.DISCONNECTED, disconnectCallback);
		handler.addIrcEventCallback(IrcHandlerEventType.CONNECTED, connectCallback);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.QUIT, quitCallback);
		handler.removeIrcEventCallback(IrcHandlerEventType.DISCONNECTED, disconnectCallback);
		handler.removeIrcEventCallback(IrcHandlerEventType.CONNECTED, connectCallback);
	}

	class QuitCallback implements IrcEventCallback {

		@Override
		public void callback(IrcHandlerEvent ie) {
			isClosingDown = true;
		}
	}

	class DisconnectCallback implements IrcEventCallback {

		@Override
		public void callback(IrcHandlerEvent ie) {
			isConnected = false;
			while (!isConnected && counter <= MAX_TRIES && !isClosingDown) {
				counter++;
				LOG.debug("Try to reconnect. This is the {}. try.", counter);
				try {
					Thread.sleep(RECONNECT_INTERVALL);
					LOG.info("Waited {}ms, try to connect again.", RECONNECT_INTERVALL);
					ie.getHandler().connect();
				} catch (InterruptedException ex) {
					LOG.warn(ex.getMessage());
				} catch (Exception ex) {
					LOG.warn(ex.getMessage());
				}
			}
		}
	}

	class ConnectCallback implements IrcEventCallback {

		@Override
		public void callback(IrcHandlerEvent ie) {
			isConnected = true;
		}
	}
}

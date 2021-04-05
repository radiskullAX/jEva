package eu.animegame.jeva.plugins;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class PingPlugin implements IrcHandlerPlugin, IrcEventCallback {

	@Override
	public void callback(IrcHandlerEvent ie) {
		String pong = ie.getMessage().substring(ie.getMessage().indexOf(":") + 1);
		ie.getHandler().sendPong(pong);
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.PING, this);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.PING, this);
	}
}

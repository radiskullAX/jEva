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
public class CloseConnectionPlugin implements IrcHandlerPlugin, IrcEventCallback {

	@Override
	public void callback(IrcHandlerEvent ie) {
		ie.getHandler().disconnect();
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.QUIT, this);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.QUIT, this);
	}
}

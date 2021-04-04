package eu.animegame.jeva.plugins;

import eu.animegame.jeva.AbstractIrcHandler;
import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class AuthenticationPlugin implements IrcHandlerPlugin, IrcEventCallback {

	@Override
	public void callback(IrcHandlerEvent ie) {
		sendAuthentication(ie.getHandler());
	}

	private void sendAuthentication(AbstractIrcHandler handler) {
		// First step: send PASS if necessary
		if (!handler.getPassword().isEmpty()) {
			handler.sendPass(handler.getPassword());
		}
		// Second step: send IRC nickname
		handler.sendNick(handler.getNick());
		// Third step: send user information
		handler.sendUser(handler.getNick(), handler.getMode(), handler.getRealName());
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.STARTUP, this);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.STARTUP, this);
	}
}

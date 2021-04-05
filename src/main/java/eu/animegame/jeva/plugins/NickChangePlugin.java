package eu.animegame.jeva.plugins;

import eu.animegame.jeva.AbstractIrcHandler;
import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 * @author radiskull
 */
public class NickChangePlugin implements IrcHandlerPlugin, IrcEventCallback {

	@Override
	public void callback(IrcHandlerEvent ie) {
		AbstractIrcHandler handler = ie.getHandler();
		if (ie.getSender().equals(handler.getNick())) {
			handler.setNick(ie.getMessage());
		}
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.NICK, this);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.NICK, this);
	}
}

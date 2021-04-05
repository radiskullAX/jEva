package eu.animegame.jeva.plugins;

import eu.animegame.jeva.AbstractIrcHandler;
import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;
import eu.animegame.jeva.utils.IrcCommandCode;

/**
 * @author radiskull
 */
public class ConnectPlugin implements IrcHandlerPlugin, IrcEventCallback {
	// TODO: Rename this class .. to something better
	@Override
	public void callback(IrcHandlerEvent ie) {
		if (ie.getCommandCode() == IrcCommandCode.RPL_ENDOFMOTD) {
			AbstractIrcHandler handler = ie.getHandler();
			handler.fireIrcHandlerEvent(
					new IrcHandlerEvent(IrcHandlerEventType.CONNECTED, null, null, null, null, 0, null, handler));
		}
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.COMMAND, this);
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.COMMAND, this);
	}
}

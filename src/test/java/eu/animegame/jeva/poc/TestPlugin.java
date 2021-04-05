package eu.animegame.jeva.poc;

import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class TestPlugin implements IrcHandlerPlugin {

	@IrcEvent(type = "PING")
	public void testEvent() {
		System.out.println("I am called testEvent");
	}

	@IrcEvent(type = "PONG")
	public void testEvent2() {
		System.out.println("I am called testEvent2");
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		// TODO Auto-generated method stub

	}

}

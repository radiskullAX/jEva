package eu.animegame.jeva.interfaces;

/**
 * @author radiskull
 */
public interface IrcHandlerPlugin {

	public void registerCallbackEvents(IrcHandler handler);

	public void unregisterCallbackEvents(IrcHandler handler);
}

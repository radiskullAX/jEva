package eu.animegame.jeva.interfaces;

/**
 * @author radiskull
 */
public interface IrcHandlerPlugin {

	public void registerCallbackEvents(IrcHandler handler);

	public void unregisterCallbackEvents(IrcHandler handler);

    /**
     * Register this plugin as a service for other plugins within the bot to use.<br>
     * TODO: Write how to be able to call it within the bot.
     * 
     * @return true if this plugin supplies methods for other plugins to use
     */
    public default boolean registerAsService() {
      return false;
    }
}

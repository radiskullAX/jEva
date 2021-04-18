package eu.animegame.jeva.core;

/**
 * @author radiskull
 */
public interface IrcHandlerPlugin {

    /**
     * Register this plugin as a service for other plugins within the bot to use.<br>
     * TODO: Write how to be able to call it within the bot.
     * 
     * @return true if this plugin supplies methods for other plugins to use
     */
    public default boolean registerAsService() {
      return false;
    }

    public default void initialize(IrcHandler handler) {};

    public default void connect(IrcHandler handler) {};

    public default void disconnect(IrcHandler handler) {};

    public default void shutdown(IrcHandler handler) {};
}

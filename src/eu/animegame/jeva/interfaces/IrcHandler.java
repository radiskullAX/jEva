package eu.animegame.jeva.interfaces;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;

/**
 *
 * @author radiskull
 */
public interface IrcHandler {

	/**
	 * Returns the name of the bot
	 * 
	 * @return Name of the bot
	 */
	public String getNick();

	public void setNick(String name);

	public String getRealName();

	public void setRealName(String name);

	public String getAdresse();

	public void setAddress(String adresse);

	public int getPort();

	public void setPort(int port);

	public String getPassword();

	public void setPassword(String password);

	public int getMode();

	public void setMode(int mode);

	public boolean addIrcHandlerPlugin(IrcHandlerPlugin plugin);

	public boolean removeIrcHandlerPlugin(IrcHandlerPlugin plugin);

	public void addIrcEventCallback(IrcHandlerEventType type, IrcEventCallback callback);

	public void removeIrcEventCallback(IrcHandlerEventType type, IrcEventCallback callback);

	/**
	 *
	 * Sends a raw message to the Server
	 *
	 * @param message
	 */
	public void sendMessage(String message);

	public IrcHandlerEvent parseMessage(String message);
}

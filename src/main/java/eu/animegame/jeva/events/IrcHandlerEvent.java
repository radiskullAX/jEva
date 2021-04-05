package eu.animegame.jeva.events;

import eu.animegame.jeva.AbstractIrcHandler;

/**
 * This is the class, which all listeners receive when an event occurs
 * 
 * @author radiskull
 *
 */
public class IrcHandlerEvent {

	private String itsSender;
	private String itsChannel;
	private AbstractIrcHandler itsHandler;
	private String itsMessage;
	private IrcHandlerEventType itsType;
	private String itsHostname;
	private int itsCode;
	private String itsSource;

	public IrcHandlerEvent(IrcHandlerEventType type, String sender, String hostname, String channel, String message,
			int code, String source, AbstractIrcHandler handler) {
		itsSender = sender;
		itsChannel = channel;
		itsHandler = handler;
		itsType = type;
		itsMessage = message;
		itsHostname = hostname;
		itsCode = code;
		itsSource = source;
	}

	public String getChannel() {
		return itsChannel;
	}

	public AbstractIrcHandler getHandler() {
		return itsHandler;
	}

	public String getSender() {
		return itsSender;
	}

	public String getHostname() {
		return itsHostname;
	}

	/**
	 * Returns the command (without that IRC specific stuff)
	 * 
	 * @return
	 */
	public String getMessage() {
		return itsMessage;
	}

	public IrcHandlerEventType getType() {
		return itsType;
	}

	public int getCommandCode() {
		return itsCode;
	}

	public String getSource() {
		return itsSource;
	}

	@Override
	public String toString() {
		return "IrcEvent: [Type: " + itsType + " Sender: " + itsSender + " Hostname: " + itsHostname + " Channel: "
				+ itsChannel + " Message: \"" + itsMessage + "\" Code: " + itsCode + "\"]";
	}
}

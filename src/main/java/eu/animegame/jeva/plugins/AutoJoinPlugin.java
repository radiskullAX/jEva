package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.animegame.jeva.events.IrcHandlerEvent;
import eu.animegame.jeva.events.IrcHandlerEventType;
import eu.animegame.jeva.interfaces.IrcEventCallback;
import eu.animegame.jeva.interfaces.IrcHandler;
import eu.animegame.jeva.interfaces.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
public class AutoJoinPlugin implements IrcHandlerPlugin {

	protected static Logger LOG = LoggerFactory.getLogger(AutoJoinPlugin.class);
	private String[] channels;
	private ConnectedCallback autoConnect;
	private InviteCallback autoInvite;
	private KickCallback autoRejoin;

	public AutoJoinPlugin(String... channel) {
		this.channels = channel;
		autoConnect = new ConnectedCallback();
		autoInvite = new InviteCallback();
		autoRejoin = new KickCallback();
	}

	@Override
	public void registerCallbackEvents(IrcHandler handler) {
		handler.addIrcEventCallback(IrcHandlerEventType.CONNECTED, autoConnect);
		handler.addIrcEventCallback(IrcHandlerEventType.KICK, autoRejoin);
		handler.addIrcEventCallback(IrcHandlerEventType.INVITE, autoInvite);
	}

	@Override
	public void unregisterCallbackEvents(IrcHandler handler) {
		handler.removeIrcEventCallback(IrcHandlerEventType.CONNECTED, autoConnect);
		handler.removeIrcEventCallback(IrcHandlerEventType.KICK, autoRejoin);
		handler.removeIrcEventCallback(IrcHandlerEventType.INVITE, autoInvite);
	}

	class ConnectedCallback implements IrcEventCallback {

		@Override
		public void callback(IrcHandlerEvent ie) {
			for (String channel : channels) {
				channel = channel.startsWith("#") ? channel : "#" + channel;
				ie.getHandler().sendJoin(channel, "");
				LOG.info("Joining Channel {}.", channel);
			}
		}
	}

	class InviteCallback implements IrcEventCallback {

		@Override
		public void callback(IrcHandlerEvent ie) {
			LOG.info("Got invitation from {} joining channel {}.", ie.getSender(), ie.getChannel());
			ie.getHandler().sendJoin(ie.getChannel(), "");
		}

	}

	class KickCallback implements IrcEventCallback {

		@Override
		public void callback(IrcHandlerEvent ie) {
			if (ie.getMessage().contains(ie.getHandler().getNick())) {
				LOG.info("Kicked from channel {}, rejoin immediately.", ie.getChannel());
				ie.getHandler().sendJoin(ie.getChannel(), "");
			}
		}

	}
}

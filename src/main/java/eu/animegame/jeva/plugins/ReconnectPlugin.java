package eu.animegame.jeva.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.core.lifecycle.Initialize;

/**
 *
 * @author radiskull
 */
public class ReconnectPlugin implements IrcHandlerPlugin {

	private final int interval;
	private final int maximumTries;
	private static Logger LOG = LoggerFactory.getLogger(ReconnectPlugin.class);
	private int counter = 0;

	public ReconnectPlugin() {
		this(10, 15000);
	}

    public ReconnectPlugin(int maxTries, int reconnectIntervall) {
      maximumTries = maxTries > 0 && maxTries < Integer.MAX_VALUE ? maxTries : Integer.MAX_VALUE;
      interval = reconnectIntervall;
    }

    @Override
    public void disconnect(IrcHandler handler) {
      if (counter <= maximumTries) {
        LOG.info("Try to reconnect {} / {} tries", counter, maximumTries);
        handler.setState(new Initialize());
        // TODO: sleep but do it in the handler
        // handler.wait(interval);
      }
      counter++;
    }

    @Override
    public void connect(IrcHandler handler) {
      counter = 0;
    }
}

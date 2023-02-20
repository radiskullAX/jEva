package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcCommandParser;
import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.exceptions.ConnectException;
import eu.animegame.jeva.core.exceptions.UnknownFormatException;

/**
 *
 * @author radiskull
 */
public class Read implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Read.class);

  @Override
  public void run(JEvaIrcClient context) {
    try {
      var message = context.readCommand();
      var event = IrcCommandParser.toIrcEvent(message);
      context.fireIrcEvent(event);
    } catch (UnknownFormatException e) {
      LOG.warn("Could not parse incoming message", e);
      LOG.debug("Unknown message: {}", e.getUnknownMessage());
    } catch (ConnectException ce) {
      LOG.warn("Failed to keep connection alive", ce);
      context.setState(new Disconnect());
    } catch (Exception e) {
      LOG.error("Failed to read", e);
      context.setState(new Shutdown());
    }
  }
}

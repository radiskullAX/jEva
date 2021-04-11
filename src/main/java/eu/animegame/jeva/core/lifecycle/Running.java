package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcMessageParser;
import eu.animegame.jeva.core.exceptions.UnknownFormatException;

public class Running implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Running.class);

  @Override
  public void run(IrcHandler context) {
    try {
      var message = context.readCommand();
      var event = IrcMessageParser.toIrcEvent(message);
      context.fireIrcEvent(event);
    } catch (UnknownFormatException e) {
      LOG.warn("Could not parse incoming message", e);
      LOG.debug("Unknown message: {}", e.getUnknownMessage());
    } catch (Exception e) {
      LOG.error("An Exception occured", e);
      context.setState(new Shutdown());
    }
  }
}

package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.exceptions.ConnectException;

/**
 *
 * @author radiskull
 */
public class Connect implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  @Override
  public void run(IrcHandler context) {
    LOG.info("Try to establish connection");

    try {
      context.connect();
      context.setState(new Read());
      context.fireLifecycleState(p -> p.connect(context));
      LOG.info("Established a connection");
    } catch (ConnectException ce) {
      LOG.warn("Failed to establish a connection", ce);
      context.setState(new Disconnect());
    } catch (Exception e) {
      LOG.error("Failed to connect", e);
      context.setState(new Shutdown());
    }
  }
}

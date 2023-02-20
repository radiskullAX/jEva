package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.JEvaIrcClient;

/**
 *
 * @author radiskull
 */
public class Disconnect implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  @Override
  public void run(JEvaIrcClient context) {
    LOG.info("Closing connection and clean up");
    context.setState(new Shutdown());
    try {
      context.fireLifecycleState(p -> p.disconnect(context));
      LOG.info("Finished clean up");
    } catch (Exception e) {
      LOG.error("Failed to disconnect", e);
      context.setState(new Shutdown());
    }
  }
}

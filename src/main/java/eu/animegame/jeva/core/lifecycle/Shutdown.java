package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.JEvaIrcClient;

/**
 *
 * @author radiskull
 */
public class Shutdown implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  @Override
  public void run(JEvaIrcClient context) {
    LOG.info("Prepare to stop engine");
    try {
      context.fireLifecycleState(p -> p.shutdown(context));
    } catch (Exception e) {
      LOG.error("Failed to shut down plugins", e);
    }
    LOG.info("Engine stopped");
    context.setState(new Initialize());
  }
}

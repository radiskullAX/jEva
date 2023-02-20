package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.JEvaIrcClient;

/**
 *
 * @author radiskull
 */
public class Initialize implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Initialize.class);

  @Override
  public void run(JEvaIrcClient context) {
    LOG.info("Engine startup");
    LOG.info("Looking up and initializing plugins");

    try {
      context.lookup();
      context.fireLifecycleState(p -> p.initialize(context));

      context.setState(new Connect());
      LOG.info("Finished to initiliaze plugins");
    } catch (Exception e) {
      LOG.error("Failed to initialize", e);
      context.setState(new Shutdown());
    }
  }
}

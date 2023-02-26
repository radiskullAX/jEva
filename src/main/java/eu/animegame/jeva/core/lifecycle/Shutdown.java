package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 *
 * @author radiskull
 */
public class Shutdown implements LifeCycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  private final LifeCycleObject object;

  public Shutdown(LifeCycleObject object) {
    this.object = object;
  }

  @Override
  public void run(LifeCycle context) {
    try {
      object.shutdown();
    } catch (Exception e) {
      LOG.error("failed to shutdown", e);
    }
    // This is important or else the lifecycle will never end
    context.setState(null);
  }
}

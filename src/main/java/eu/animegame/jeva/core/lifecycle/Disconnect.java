package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 *
 * @author radiskull
 */
public class Disconnect implements LifeCycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  private final LifeCycleObject object;

  public Disconnect(LifeCycleObject object) {
    this.object = object;
  }

  @Override
  public void run(LifeCycle context) {
    try {
      object.disconnect();
    } catch (Exception e) {
      LOG.error("failed to disconnect", e);
    }
    context.setState(new Shutdown(object));
  }
}

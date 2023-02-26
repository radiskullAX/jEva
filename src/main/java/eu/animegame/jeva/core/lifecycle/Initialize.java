package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 *
 * @author radiskull
 */
public class Initialize implements LifeCycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Initialize.class);

  private final LifeCycleObject object;

  public Initialize(LifeCycleObject object) {
    this.object = object;
  }

  @Override
  public void run(LifeCycle context) {
    try {
      object.initialize();
      context.setState(new Connect(object));
    } catch (Exception e) {
      LOG.error("failed to initialize", e);
      context.setState(new Shutdown(object));
    }
  }
}

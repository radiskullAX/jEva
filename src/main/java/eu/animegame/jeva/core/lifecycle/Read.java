package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 *
 * @author radiskull
 */
public class Read implements LifeCycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Read.class);

  private final LifeCycleObject object;

  public Read(LifeCycleObject object) {
    this.object = object;
  }

  @Override
  public void run(LifeCycle context) {
    try {
      object.read();
    } catch (Exception e) {
      LOG.error("failed to read", e);
    }
    context.setState(new Disconnect(object));
  }
}

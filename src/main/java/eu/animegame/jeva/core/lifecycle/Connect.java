package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 *
 * @author radiskull
 */
public class Connect implements LifeCycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  private final LifeCycleObject object;

  public Connect(LifeCycleObject object) {
    this.object = object;
  }

  @Override
  public void run(LifeCycle context) {
    try {
      object.connect();
      context.setState(new Read(object));
    } catch (Exception e) {
      LOG.error("failed to connect", e);
      context.setState(new Disconnect(object));
    }
  }
}

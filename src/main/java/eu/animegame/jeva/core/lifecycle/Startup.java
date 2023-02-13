package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;

/**
 *
 * @author radiskull
 */
@Deprecated
public class Startup implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Startup.class);

  @Override
  public void run(IrcHandler context) {
    // TODO: remove this cycle, it's obsolete
    LOG.info("Engine starting up");
    context.setState(new Initialize());
  }
}

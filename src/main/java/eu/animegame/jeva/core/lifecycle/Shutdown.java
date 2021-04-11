package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;

public class Shutdown implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  @Override
  public void run(IrcHandler context) {
    LOG.info("Prepare to stop engine");
    try {
      fireLifecycleState(context);
    } catch (Exception e) {
      LOG.error("Exception occured", e);
    }
    LOG.info("Engine stopped");
    context.setState(new Startup());
  }

  private void fireLifecycleState(IrcHandler context) {
    context.getPlugins().stream().forEach(p -> p.shutdown(context));
  }
}

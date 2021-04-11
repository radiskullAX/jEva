package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;

public class Disconnect implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  @Override
  public void run(IrcHandler context) {
    LOG.info("Closing connection and clean up");
    context.setState(new Shutdown());
    try {
      fireLifecycleState(context);
      LOG.info("Finished clean up");
    } catch (Exception e) {
      LOG.error("Exception occured", e);
      context.setState(new Shutdown());
    }
  }

  private void fireLifecycleState(IrcHandler context) {
    context.getPlugins().stream().forEach(p -> p.disconnect(context));
  }
}

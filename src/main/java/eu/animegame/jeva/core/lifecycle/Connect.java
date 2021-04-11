package eu.animegame.jeva.core.lifecycle;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;

public class Connect implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Connect.class);

  @Override
  public void run(IrcHandler context) {
    LOG.info("Try to establish connection");

    try {
      context.createConnection();
      context.setState(new Running());
      fireLifecycleState(context);
      LOG.info("Established a connection");
    } catch (IOException ioe) {
      LOG.warn("Failed to establish a connection", ioe);
      context.setState(new Disconnect());
    } catch (Exception e) {
      LOG.error("Exception occured", e);
      context.setState(new Shutdown());
    }
  }

  private void fireLifecycleState(IrcHandler context) {
    context.getPlugins().stream().forEach(p -> p.connect(context));
  }
}

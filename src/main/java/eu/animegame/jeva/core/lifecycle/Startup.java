package eu.animegame.jeva.core.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.exceptions.LifecycleException;

public class Startup implements LifecycleState {

  private static final Logger LOG = LoggerFactory.getLogger(Startup.class);

  @Override
  public void run(IrcHandler context) {
    LOG.info("Engine starting up");

    var config = context.getConfiguration();
    try {
      validateParameter(IrcHandler.PROP_NICK, config.getProperty(IrcHandler.PROP_NICK));
      validateParameter(IrcHandler.PROP_SERVER, config.getProperty(IrcHandler.PROP_SERVER));
      validateParameter(IrcHandler.PROP_PORT, config.getProperty(IrcHandler.PROP_PORT));
      context.setState(new Initialize());
      LOG.info("Config checks completed");
    } catch (LifecycleException e) {
      LOG.error("Failed to startup", e);
      context.setState(new Shutdown());
    }
  }

  private void validateParameter(String name, String value) throws LifecycleException {
    if (value == null || value.isBlank()) {
      throw new LifecycleException(
          "The parameter '" + name + "' is not set! Please set the property in the handler config");
    }
  }
}

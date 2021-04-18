package eu.animegame.jeva.core.lifecycle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import eu.animegame.jeva.core.IrcHandler;

/**
 *
 * @author radiskull
 */
class StartupTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Startup();

  @Test
  public void testSetNextLifecycle() {
    var config = buildConfig();

    when(handler.getConfiguration()).thenReturn(config);
    state.run(handler);

    LifecycleHelper.verifySetState(handler, Initialize.class);
  }

  @ParameterizedTest
  @ValueSource(strings = {IrcHandler.PROP_NICK, IrcHandler.PROP_SERVER, IrcHandler.PROP_PORT})
  public void testParameterIsMissing(String parameter) {
    var config = buildConfig();
    config.remove(parameter);

    when(handler.getConfiguration()).thenReturn(config);
    state.run(handler);

    LifecycleHelper.verifySetState(handler, Shutdown.class);
  }

  private Properties buildConfig() {
    var config = new Properties();
    config.put(IrcHandler.PROP_NICK, "TestBot");
    config.put(IrcHandler.PROP_SERVER, "irc.testserver.eu");
    config.put(IrcHandler.PROP_PORT, "6667");
    config.put(IrcHandler.PROP_REAL_NAME, "jEva");
    config.put(IrcHandler.PROP_MODE, "4");
    return config;
  }
}

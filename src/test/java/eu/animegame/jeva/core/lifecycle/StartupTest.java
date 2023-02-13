package eu.animegame.jeva.core.lifecycle;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;

/**
 *
 * @author radiskull
 */
@Deprecated
class StartupTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Startup();

  @Test
  public void testSetNextLifecycle() {
    state.run(handler);

    LifecycleHelper.verifySetState(handler, Initialize.class);
  }
}

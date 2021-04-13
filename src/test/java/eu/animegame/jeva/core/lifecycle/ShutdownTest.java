package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;

class ShutdownTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Shutdown();

  @Test
  void testPluginsAreNoticed() {
    state.run(handler);

    verify(handler).fireLifecycleState(any());

    LifecycleHelper.verifySetState(handler, Startup.class);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(handler).fireLifecycleState(any());

    state.run(handler);

    LifecycleHelper.verifySetState(handler, Startup.class);
  }
}

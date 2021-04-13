package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;

class InitializeTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Initialize();

  @Test
  void testPluginsAreNoticed() {
    state.run(handler);

    verify(handler).fireLifecycleState(any());

    LifecycleHelper.verifySetState(handler, Connect.class);
  }

  @Test
  void testHandlerDoesLookup() {
    state.run(handler);

    verify(handler).lookup();

    LifecycleHelper.verifySetState(handler, Connect.class);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(handler).fireLifecycleState(any());

    state.run(handler);

    LifecycleHelper.verifySetState(handler, Shutdown.class);
  }

}

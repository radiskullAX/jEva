package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;

class InitializeTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state;

  @BeforeEach
  void init() {
    state = new Initialize();
  }

  @Test
  void testPluginsAreInitialized() {
    state.run(handler);

    verify(handler).fireLifecycleState(any());
    verify(handler).lookup();

    LifecycleHelper.verifySetState(handler, Connect.class);
  }

  @Test
  void testPluginAreFailing() {
    doThrow(new RuntimeException()).when(handler).fireLifecycleState(any());

    state.run(handler);

    LifecycleHelper.verifySetState(handler, Shutdown.class);
  }

}

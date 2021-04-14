package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;

class ShutdownTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Shutdown();

  @Test
  void testSetNextLifecycle() {
    state.run(handler);
    LifecycleHelper.verifySetState(handler, Startup.class);
  }

  @Test
  void testPluginsAreNoticed() {
    IrcHandler realHandler = new IrcHandler(new Properties());
    IrcHandlerPlugin plugin = mock(IrcHandlerPlugin.class);
    realHandler.addPlugin(plugin);
    state.run(realHandler);

    verify(plugin).shutdown(realHandler);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(handler).fireLifecycleState(any());

    state.run(handler);

    LifecycleHelper.verifySetState(handler, Startup.class);
  }
}

package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.Connection;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;

/**
 *
 * @author radiskull
 */
class DisconnectTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Disconnect();

  @Test
  void testSetNextLifecycle() {
    state.run(handler);
    LifecycleHelper.verifySetState(handler, Shutdown.class);
  }

  @Test
  void testPluginsAreNoticed() {
    Connection connection = mock(Connection.class);
    IrcHandler realHandler = new IrcHandler(connection);
    IrcHandlerPlugin plugin = mock(IrcHandlerPlugin.class);
    realHandler.addPlugin(plugin);
    state.run(realHandler);

    verify(plugin).disconnect(realHandler);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(handler).fireLifecycleState(any());

    state.run(handler);

    LifecycleHelper.verifySetState(handler, 2, Shutdown.class);
  }
}

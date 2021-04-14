package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.IrcHandlerPlugin;
import eu.animegame.jeva.core.exceptions.ConnectException;

class ConnectTest {

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Connect();

  @Test
  void testSetNextLifecycle() {
    state.run(handler);
    LifecycleHelper.verifySetState(handler, Running.class);
  }

  @Test
  void testPluginsAreNoticed() {
    IrcHandler realHandler = new IrcHandler(new Properties());
    IrcHandlerPlugin plugin = mock(IrcHandlerPlugin.class);
    realHandler.addPlugin(plugin);
    state.run(realHandler);

    verify(plugin).connect(realHandler);
  }

  @Test
  void testConnectSuccessful() throws ConnectException, Exception {
    state.run(handler);

    verify(handler).createConnection();

    LifecycleHelper.verifySetState(handler, Running.class);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(handler).fireLifecycleState(any());

    state.run(handler);

    LifecycleHelper.verifySetState(handler, 2, Shutdown.class);
  }

  @Test
  void testConnectfails() throws ConnectException, Exception {
    doThrow(new ConnectException()).when(handler).createConnection();

    state.run(handler);

    LifecycleHelper.verifySetState(handler, Disconnect.class);
  }

  @Test
  void testConnectionfails() throws ConnectException, Exception {
    doThrow(new Exception()).when(handler).createConnection();

    state.run(handler);

    LifecycleHelper.verifySetState(handler, Shutdown.class);
  }
}

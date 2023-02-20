package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.Connection;
import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.JEvaIrcPlugin;
import eu.animegame.jeva.core.exceptions.ConnectException;

/**
 *
 * @author radiskull
 */
class ConnectTest {

  private JEvaIrcClient jEvaClient = mock(JEvaIrcClient.class);

  private LifecycleState state = new Connect();

  @Test
  void testSetNextLifecycle() {
    state.run(jEvaClient);
    LifecycleHelper.verifySetState(jEvaClient, Read.class);
  }

  @Test
  void testPluginsAreNoticed() {
    Connection connection = mock(Connection.class);
    JEvaIrcClient realJEvaClient = new JEvaIrcClient(connection);
    JEvaIrcPlugin plugin = mock(JEvaIrcPlugin.class);
    realJEvaClient.addPlugin(plugin);
    state.run(realJEvaClient);

    verify(plugin).connect(realJEvaClient);
  }

  @Test
  void testConnectSuccessful() throws ConnectException, Exception {
    state.run(jEvaClient);

    verify(jEvaClient).connect();

    LifecycleHelper.verifySetState(jEvaClient, Read.class);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(jEvaClient).fireLifecycleState(any());

    state.run(jEvaClient);

    LifecycleHelper.verifySetState(jEvaClient, 2, Shutdown.class);
  }

  @Test
  void testConnectfails() throws ConnectException, Exception {
    doThrow(new ConnectException()).when(jEvaClient).connect();

    state.run(jEvaClient);

    LifecycleHelper.verifySetState(jEvaClient, Disconnect.class);
  }

  @Test
  void testConnectionfails() throws ConnectException, Exception {
    doThrow(new Exception()).when(jEvaClient).connect();

    state.run(jEvaClient);

    LifecycleHelper.verifySetState(jEvaClient, Shutdown.class);
  }
}

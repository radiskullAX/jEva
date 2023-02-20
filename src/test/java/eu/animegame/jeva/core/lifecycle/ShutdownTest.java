package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.Connection;
import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.JEvaIrcPlugin;

/**
 *
 * @author radiskull
 */
class ShutdownTest {

  private JEvaIrcClient jEvaClient = mock(JEvaIrcClient.class);

  private LifecycleState state = new Shutdown();

  @Test
  void testSetNextLifecycle() {
    state.run(jEvaClient);
    LifecycleHelper.verifySetState(jEvaClient, Initialize.class);
  }

  @Test
  void testPluginsAreNoticed() {
    Connection connection = mock(Connection.class);
    JEvaIrcClient realJEvaClient = new JEvaIrcClient(connection);
    JEvaIrcPlugin plugin = mock(JEvaIrcPlugin.class);
    realJEvaClient.addPlugin(plugin);
    state.run(realJEvaClient);

    verify(plugin).shutdown(realJEvaClient);
  }

  @Test
  void testPluginFiresException() {
    doThrow(new RuntimeException()).when(jEvaClient).fireLifecycleState(any());

    state.run(jEvaClient);

    LifecycleHelper.verifySetState(jEvaClient, Initialize.class);
  }
}

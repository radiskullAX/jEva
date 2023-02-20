package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.exceptions.ConnectException;

/**
 *
 * @author radiskull
 */
class ReadTest {

  private static final String MESSAGE = "PING :123456789";

  private JEvaIrcClient jEvaClient = mock(JEvaIrcClient.class);

  private LifecycleState state = new Read();

  @Test
  void testIrcEventIsFired() throws ConnectException, Exception {
    when(jEvaClient.readCommand()).thenReturn(MESSAGE);
    state.run(jEvaClient);

    verify(jEvaClient).fireIrcEvent(any());

    verify(jEvaClient, never()).setState(any());
  }

  @Test
  void testReadCommandSuccessful() throws ConnectException, Exception {
    when(jEvaClient.readCommand()).thenReturn(MESSAGE);
    state.run(jEvaClient);

    verify(jEvaClient).readCommand();

    verify(jEvaClient, never()).setState(any());
  }

  @Test
  void testMessageFormatFails() throws ConnectException, Exception {
    when(jEvaClient.readCommand()).thenReturn("Wrong_Format");
    state.run(jEvaClient);

    verify(jEvaClient, never()).setState(any());
  }

  @Test
  void testConnectionfails() throws ConnectException, Exception {
    doThrow(new ConnectException()).when(jEvaClient).readCommand();
    state.run(jEvaClient);

    LifecycleHelper.verifySetState(jEvaClient, Disconnect.class);
  }

  @Test
  void testReadCommandFails() throws ConnectException, Exception {
    state.run(jEvaClient);

    LifecycleHelper.verifySetState(jEvaClient, Shutdown.class);
  }
}

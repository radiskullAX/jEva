package eu.animegame.jeva.core.lifecycle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.exceptions.ConnectException;

/**
 *
 * @author radiskull
 */
class ReadTest {

  private static final String MESSAGE = "PING :123456789";

  private IrcHandler handler = mock(IrcHandler.class);

  private LifecycleState state = new Read();

  @Test
  void testIrcEventIsFired() throws ConnectException, Exception {
    when(handler.readCommand()).thenReturn(MESSAGE);
    state.run(handler);

    verify(handler).fireIrcEvent(any());

    verify(handler, never()).setState(any());
  }

  @Test
  void testReadCommandSuccessful() throws ConnectException, Exception {
    when(handler.readCommand()).thenReturn(MESSAGE);
    state.run(handler);

    verify(handler).readCommand();

    verify(handler, never()).setState(any());
  }

  @Test
  void testMessageFormatFails() throws ConnectException, Exception {
    when(handler.readCommand()).thenReturn("Wrong_Format");
    state.run(handler);

    verify(handler, never()).setState(any());
  }

  @Test
  void testConnectionfails() throws ConnectException, Exception {
    doThrow(new ConnectException()).when(handler).readCommand();
    state.run(handler);

    LifecycleHelper.verifySetState(handler, Disconnect.class);
  }

  @Test
  void testReadCommandFails() throws ConnectException, Exception {
    state.run(handler);

    LifecycleHelper.verifySetState(handler, Shutdown.class);
  }
}

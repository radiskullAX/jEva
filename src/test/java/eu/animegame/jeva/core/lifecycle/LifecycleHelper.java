package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.core.IrcHandler;

/**
 *
 * @author radiskull
 */
public class LifecycleHelper {

  private LifecycleHelper() {}

  public static void verifySetState(IrcHandler handler, Class<? extends LifecycleState> expectedState) {
    ArgumentCaptor<LifecycleState> captor = ArgumentCaptor.forClass(LifecycleState.class);
    verify(handler).setState(captor.capture());
    assertEquals(expectedState, captor.getValue().getClass());
  }

  public static void verifySetState(IrcHandler handler, int numberOfCalls,
      Class<? extends LifecycleState> expectedState) {
    ArgumentCaptor<LifecycleState> captor = ArgumentCaptor.forClass(LifecycleState.class);
    verify(handler, times(numberOfCalls)).setState(captor.capture());
    assertEquals(expectedState, captor.getValue().getClass());
  }
}

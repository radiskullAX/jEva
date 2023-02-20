package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.core.JEvaIrcClient;

/**
 *
 * @author radiskull
 */
public class LifecycleHelper {

  private LifecycleHelper() {}

  public static void verifySetState(JEvaIrcClient jEvaClient, Class<? extends LifecycleState> expectedState) {
    ArgumentCaptor<LifecycleState> captor = ArgumentCaptor.forClass(LifecycleState.class);
    verify(jEvaClient).setState(captor.capture());
    assertEquals(expectedState, captor.getValue().getClass());
  }

  public static void verifySetState(JEvaIrcClient jEvaClient, int numberOfCalls,
      Class<? extends LifecycleState> expectedState) {
    ArgumentCaptor<LifecycleState> captor = ArgumentCaptor.forClass(LifecycleState.class);
    verify(jEvaClient, times(numberOfCalls)).setState(captor.capture());
    assertEquals(expectedState, captor.getValue().getClass());
  }
}

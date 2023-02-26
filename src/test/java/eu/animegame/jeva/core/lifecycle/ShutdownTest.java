package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.exceptions.LifeCycleException;

/**
 *
 * @author radiskull
 */
class ShutdownTest extends LifeCycleStateBaseTest {

  public ShutdownTest() {
    super();
    state = new Shutdown(lifeCycleObject);
  }

  @Test
  void run() throws LifeCycleException {
    state.run(lifeCycle);

    verify(lifeCycleObject).shutdown();
    verify(lifeCycle).setState(null);
  }

  @Test
  void runThrowsException() throws LifeCycleException {
    doThrow(LifeCycleException.class).when(lifeCycleObject).shutdown();

    assertDoesNotThrow(() -> state.run(lifeCycle));

    verify(lifeCycle).setState(null);
  }
}

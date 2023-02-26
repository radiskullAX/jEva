package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.exceptions.LifeCycleException;

/**
 *
 * @author radiskull
 */
class ReadTest extends LifeCycleStateBaseTest {

  public ReadTest() {
    super();
    state = new Read(lifeCycleObject);
  }

  @Test
  void run() throws LifeCycleException {
    state.run(lifeCycle);

    verify(lifeCycleObject).read();
    verify(lifeCycle).setState(any(Disconnect.class));
  }

  @Test
  void runThrowsException() throws LifeCycleException {
    doThrow(LifeCycleException.class).when(lifeCycleObject).read();

    assertDoesNotThrow(() -> state.run(lifeCycle));

    verify(lifeCycle).setState(any(Disconnect.class));
  }
}

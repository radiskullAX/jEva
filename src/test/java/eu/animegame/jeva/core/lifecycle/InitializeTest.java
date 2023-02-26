package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.exceptions.LifeCycleException;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class InitializeTest extends LifeCycleStateBaseTest {

  public InitializeTest() {
    super();
    state = new Initialize(lifeCycleObject);
  }

  @Test
  void run() throws LifeCycleException {
    state.run(lifeCycle);

    verify(lifeCycleObject).initialize();
    verify(lifeCycle).setState(any(Connect.class));
  }

  @Test
  void runThrowsException() throws LifeCycleException {
    doThrow(LifeCycleException.class).when(lifeCycleObject).initialize();

    assertDoesNotThrow(() -> state.run(lifeCycle));

    verify(lifeCycle).setState(any(Shutdown.class));
  }
}

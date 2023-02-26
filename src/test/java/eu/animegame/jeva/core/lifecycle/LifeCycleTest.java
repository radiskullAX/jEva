package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import java.time.Duration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 * 
 *
 * @author radiskull
 */
class LifeCycleTest {

  private LifeCycle lifeCycle;

  private LifeCycleState state;

  /**
   * 
   */
  private LifeCycleTest() {
    state = mock(TestState.class);
    lifeCycle = new LifeCycle(state);
  }

  @Test
  @Tag(Tags.TIMED)
  void start() {
    TestState secondState = spy(new TestState(null));
    TestState firstState = spy(new TestState(secondState));

    lifeCycle = new LifeCycle(firstState);

    assertTimeoutPreemptively(Duration.ofSeconds(5), () -> lifeCycle.start());

    verify(firstState).run(lifeCycle);
    verify(secondState).run(lifeCycle);
    assertNull(lifeCycle.getState());
  }

  @Test
  void getState() {
    assertNull(lifeCycle.getState());
  }

  @Test
  void setState() {
    LifeCycleState newState = new TestState(null);
    lifeCycle.setState(newState);

    assertEquals(newState, lifeCycle.getState());
  }

  private class TestState implements LifeCycleState {

    private final LifeCycleState nextState;

    public TestState(LifeCycleState nextState) {
      this.nextState = nextState;
    }

    @Override
    public void run(LifeCycle context) {
      context.setState(nextState);
    }
  }
}

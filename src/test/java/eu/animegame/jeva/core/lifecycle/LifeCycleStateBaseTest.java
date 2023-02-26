package eu.animegame.jeva.core.lifecycle;

import static org.mockito.Mockito.mock;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.LifeCycleState;

/**
 * 
 *
 * @author radiskull
 */
public class LifeCycleStateBaseTest {

  protected LifeCycle lifeCycle;

  protected LifeCycleObject lifeCycleObject;

  protected LifeCycleState state;

  public LifeCycleStateBaseTest() {
    lifeCycle = mock(LifeCycle.class);
    lifeCycleObject = mock(LifeCycleObject.class);
  }
}

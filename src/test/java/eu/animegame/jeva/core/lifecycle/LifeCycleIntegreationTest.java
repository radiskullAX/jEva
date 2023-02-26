package eu.animegame.jeva.core.lifecycle;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.time.Duration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.LifeCycle;
import eu.animegame.jeva.core.exceptions.LifeCycleException;

/**
 * 
 *
 * @author radiskull
 */
@Tag(Tags.INTEGRATION)
public class LifeCycleIntegreationTest {

  private LifeCycle lifeCycle;

  @Test
  @Tag(Tags.TIMED)
  void start() throws LifeCycleException {
    LifeCycleObject object = mock(LifeCycleObject.class);

    lifeCycle = new LifeCycle(new Initialize(object));

    assertTimeoutPreemptively(Duration.ofSeconds(5), () -> lifeCycle.start());

    verify(object).initialize();
    verify(object).connect();
    verify(object).read();
    verify(object).disconnect();
    verify(object).shutdown();
    assertNull(lifeCycle.getState());
  }
}

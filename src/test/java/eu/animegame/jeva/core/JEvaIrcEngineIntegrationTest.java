package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.exceptions.ConnectionException;

/**
 * 
 *
 * @author radiskull
 */
@Tag(Tags.INTEGRATION)
@Tag(Tags.TIMED)
public class JEvaIrcEngineIntegrationTest {

  private JEvaIrcEngine engine;

  private Connection connection;

  private IrcPluginController pluginController;

  private JEvaIrcPlugin plugin;

  public JEvaIrcEngineIntegrationTest() {
    connection = mock(Connection.class);
    pluginController = mock(IrcPluginController.class);
    engine = new JEvaIrcEngine(connection, pluginController);
  }

  @Test
  void start() throws Exception {
    plugin = new TestPlugin();
    pluginController = spy(new IrcPluginController(engine));
    engine = new JEvaIrcEngine(connection, pluginController);
    engine.addPlugin(plugin);

    assertTimeoutPreemptively(Duration.ofSeconds(5), () -> engine.start());

    verify(pluginController).lookup();
    verify(pluginController, times(4)).fireLifecycleState(any());

    verify(connection, never()).read();
    verify(pluginController, never()).fireIrcEvent(any());

    verify(connection).connect();
    verify(connection).disconnect();
  }

  @Test
  void stop() throws ConnectionException {
    when(connection.read()).thenReturn("NOTICE this is a test");
    
    assertTimeoutPreemptively(Duration.ofSeconds(5), () ->
      {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        var thread = new Thread(new TestWorker(engine, countDownLatch));
        thread.start();

        while (!engine.isRunning()) {
          Thread.sleep(10);
        }

        assertTrue(engine.isRunning());
        engine.stop();
        countDownLatch.await();
      });
    assertFalse(engine.isRunning());
  }

  @Test
  void stopThroughThreadInterruption() throws ConnectionException {
    when(connection.read()).thenReturn("NOTICE this is a test");

    assertTimeoutPreemptively(Duration.ofSeconds(5), () ->
      {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        var thread = new Thread(new TestWorker(engine, countDownLatch));
        thread.start();

        while (!engine.isRunning()) {
          Thread.sleep(10);
        }

        assertTrue(engine.isRunning());
        thread.interrupt();
        countDownLatch.await();
      });
    assertFalse(engine.isRunning());
  }

  @Test
  void stopThroughConnectionReadException() throws ConnectionException {
    doThrow(ConnectionException.class).when(connection).read();

    assertTimeoutPreemptively(Duration.ofSeconds(5), () -> engine.start());
    assertFalse(engine.isRunning());
  }

  private class TestPlugin implements JEvaIrcPlugin {

    @Override
    public void connect(JEvaIrcEngine jEvaIrcEngine) {
      // should stop the read phase immediately but not the lifecycle
      jEvaIrcEngine.stop();
    }
  }

  private class TestWorker implements Runnable {

    private final JEvaIrcEngine jEvaIrcEngine;
    private final CountDownLatch latch;

    public TestWorker(JEvaIrcEngine jEvaIrcEngine, CountDownLatch latch) {
      this.jEvaIrcEngine = jEvaIrcEngine;
      this.latch = latch;
    }

    @Override
    public void run() {
      // would run endlessly until stop is called
      jEvaIrcEngine.start();
      latch.countDown();
    }
  }
}

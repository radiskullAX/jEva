package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import eu.animegame.jeva.core.lifecycle.Initialize;
import eu.animegame.jeva.core.lifecycle.LifecycleState;
import eu.animegame.jeva.core.lifecycle.Shutdown;

/**
 *
 * @author radiskull
 */
// TODO: Use @Tag annotation more, look into nested tests
class JEvaIrcClientTest {

  private static final String STARTUP_STATE = "Initialize";

  private static final String COMMAND = "TestCommand";

  private JEvaIrcClient jEvaClient;

  private IrcPluginController pluginController;

  private Connection connection;

  private JEvaIrcClientTest() {
    connection = mock(Connection.class);
    pluginController = mock(IrcPluginController.class);
    jEvaClient = new JEvaIrcClient(connection, pluginController);
  }

  @ParameterizedTest
  @MethodSource("IrcJEvaClientProvider")
  void testConstructors(JEvaIrcClient jEvaClient) {
    assertNotNull(jEvaClient.getConfig());
    assertNotNull(jEvaClient.getState());
    assertNotNull(jEvaClient.getConnection());
    assertNotNull(jEvaClient.getPluginController());
  }

  private static Stream<Arguments> IrcJEvaClientProvider() {
    var connection = mock(Connection.class);
    var pluginController = mock(IrcPluginController.class);
    var ircConfig = mock(IrcConfig.class);
    return Stream.of(
        Arguments.of(new JEvaIrcClient()),
        Arguments.of(new JEvaIrcClient(connection)),
        Arguments.of(new JEvaIrcClient(connection, ircConfig)),
        Arguments.of(new JEvaIrcClient(connection, pluginController))
        );
  }
  
  @Test
  void getConfig() {
    // test constructor injection too
    IrcConfig config = jEvaClient.getConfig();

    assertNotNull(config);
  }

  @Test
  void addPlugin() {
    var plugin = mock(JEvaIrcPlugin.class);
    jEvaClient.addPlugin(plugin);

    verify(pluginController).addPlugin(plugin);
  }

  @Test
  void removePlugin() {
    var plugin = mock(JEvaIrcPlugin.class);
    jEvaClient.removePlugin(plugin);

    verify(pluginController).removePlugin(plugin);
  }

  @Test
  void getPlugins() {
    jEvaClient.getPlugins();

    verify(pluginController).getPlugins();
  }

  @Test
  void getPlugin() {
    var pluginClass = JEvaIrcPlugin.class;
    jEvaClient.getPlugin(pluginClass);

    verify(pluginController).getPlugin(pluginClass);
  }

  @Test
  void lookup() {
    jEvaClient.lookup();

    verify(pluginController).lookup();
  }

  @Test
  void fireLifecycleState() {
    Consumer<JEvaIrcPlugin> function = p -> p.connect(jEvaClient);

    jEvaClient.fireLifecycleState(function);
    verify(pluginController).fireLifecycleState(function);
  }

  @Test
  void fireIrcEvent() {
    IrcBaseEvent event = mock(IrcBaseEvent.class);
    jEvaClient.fireIrcEvent(event);

    verify(pluginController).fireIrcEvent(event);
  }

  @Test
  void connect() throws Exception {
    jEvaClient.connect();

    verify(connection).connect();
  }

  @Test
  void disconnect() throws Exception {
    jEvaClient.disconnect();

    verify(connection).disconnect();
  }

  @Test
  void readCommand() throws Exception {
    when(connection.read()).thenReturn(COMMAND);

    var actual = jEvaClient.readCommand();

    assertEquals(COMMAND, actual);
  }

  @Test
  void sendCommand() throws Exception {
    var command = mock(IrcCommand.class);
    when(command.build()).thenReturn(COMMAND);

    jEvaClient.sendCommand(command);

    verify(connection).write(COMMAND);
  }

  @Test
  void sendCommandWithNullArgument() throws Exception {
    jEvaClient.sendCommand(null);

    verify(connection, never()).write(anyString());
  }

  @Test
  void sendCommandThrowsException() throws Exception {
    var command = mock(IrcCommand.class);
    when(command.build()).thenReturn(COMMAND);
    doThrow(Exception.class).when(connection).write(anyString());

    assertDoesNotThrow(() -> jEvaClient.sendCommand(command));
  }

  @Test
  void builder() {
    var builder = JEvaIrcClient.builder();

    assertNotNull(builder);
    assertEquals(JEvaIrcClientBuilder.class, builder.getClass());
  }

  @Test
  @Tag("timed")
  void stopThroughMethodcall() throws InterruptedException {
    LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    jEvaClient.setState(testState);
    var thread = new Thread(new TestWorker(jEvaClient, countDownLatch));
    thread.start();

    // TODO: it kinda sucks but rather run a loop and check if the client is ready, then do stuff
    Thread.sleep(50);

    assertTrue(jEvaClient.isRunning());
    jEvaClient.stop();
    countDownLatch.await();
    assertFalse(jEvaClient.isRunning());
  }

  @Test
  @Tag("timed")
  void stopThroughLifecycleEnd() throws InterruptedException {
    LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    jEvaClient.setState(testState);
    var thread = new Thread(new TestWorker(jEvaClient, countDownLatch));
    thread.start();

    Thread.sleep(50);

    assertTrue(jEvaClient.isRunning());
    jEvaClient.setState(new Initialize());
    countDownLatch.await();
    assertFalse(jEvaClient.isRunning());
  }

  @Test
  @Tag("timed")
  void stopThroughThreadInterruption() throws InterruptedException {
    final LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    jEvaClient.setState(testState);
    var thread = new Thread(new TestWorker(jEvaClient, countDownLatch));
    thread.start();

    Thread.sleep(50);

    assertTrue(jEvaClient.isRunning());
    thread.interrupt();
    countDownLatch.await();
    assertFalse(jEvaClient.isRunning());
  }

  @Test
  void startResetsStateWhenFinished() throws Exception {
    JEvaIrcClient spyClient = spy(jEvaClient);
    spyClient.setState(new Shutdown());

    spyClient.start();

    verify(connection).setConfig(jEvaClient.getConfig());
    verify(connection).disconnect();
    assertFalse(spyClient.isRunning());
    assertEquals(STARTUP_STATE, spyClient.getState());
  }

  @Test
  void startClosingConnectionThrowsException() throws Exception {
    JEvaIrcClient spyClient = spy(jEvaClient);
    spyClient.setState(new Shutdown());
    doThrow(IOException.class).when(connection).disconnect();

    spyClient.start();

    verify(connection).disconnect();
    assertFalse(spyClient.isRunning());
    assertEquals(STARTUP_STATE, spyClient.getState());
  }

  private class TestWorker implements Runnable {

    private final JEvaIrcClient jEvaClient;
    private final CountDownLatch latch;

    public TestWorker(JEvaIrcClient jEvaClient, CountDownLatch latch) {
      this.jEvaClient = jEvaClient;
      this.latch = latch;
    }

    @Override
    public void run() {
      // would run endlessly until stop is called
      jEvaClient.start();
      latch.countDown();
    }

  }
}

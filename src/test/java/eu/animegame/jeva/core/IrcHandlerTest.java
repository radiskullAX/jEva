package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.lifecycle.Initialize;
import eu.animegame.jeva.core.lifecycle.LifecycleState;
import eu.animegame.jeva.core.lifecycle.Shutdown;
import eu.animegame.jeva.irc.commands.Pong;

/**
 *
 * @author radiskull
 */
class IrcHandlerTest {

  private static final String STARTUP_STATE = "Initialize";

  private IrcHandler handler;

  private TestPlugin plugin;

  private Connection connection;

  @BeforeEach
  public void init() {
    connection = mock(Connection.class);
    handler = new IrcHandler(connection);
    plugin = mock(TestPlugin.class);
  }

  @Test
  @Disabled
  public void testGetConfig() {
    // Connection connection = mock(Connection.class);
    // Properties config = new Properties();
    // var key = "Key";
    // var value = "Value";
    // config.put(key, value);
    //
    // IrcHandler handler = new IrcHandler(connection);
    // var handlerConfig = handler.getConfig();
    //
    // assertEquals(1, handlerConfig.size());
    // assertEquals(value, handlerConfig.get(key));
  }

  @Test
  public void testAddPlugin() {
    handler.addPlugin(plugin);
    assertEquals(1, handler.getPlugins().size());

    handler.addPlugin(plugin);
    assertEquals(1, handler.getPlugins().size());
  }

  @Test
  public void testRemovePlugin() {
    handler.addPlugin(plugin);
    assertEquals(1, handler.getPlugins().size());

    handler.removePlugin(new TestPlugin());
    assertEquals(1, handler.getPlugins().size());

    handler.removePlugin(plugin);
    assertEquals(0, handler.getPlugins().size());
  }

  @Test
  public void testGetPluginsIsUnmodifable() {
    handler.addPlugin(plugin);

    var plugins = handler.getPlugins();
    assertEquals(1, plugins.size());
    assertThrows(UnsupportedOperationException.class, () -> plugins.remove(0));
    assertThrows(UnsupportedOperationException.class, () -> plugins.add(plugin));
  }

  @Test
  public void testFireLifecycleEvent() {
    handler.addPlugin(plugin);

    handler.fireLifecycleState(p -> p.connect(handler));
    verify(plugin).connect(handler);
  }

  @Test
  public void testFireIrcEvents() {
    handler.addPlugin(plugin);
    handler.lookup();

    IrcBaseEvent ping = new IrcBaseEvent("PING", "123", "source");
    handler.fireIrcEvent(ping);
    verify(plugin).parsePing(ping);

    IrcBaseEvent privmsg = new IrcBaseEvent(":test.user", "PRIVMSG", "It's a test", "source");
    handler.fireIrcEvent(privmsg);
    verify(plugin).parseMsg(privmsg);

    IrcBaseEvent cmd = new IrcBaseEvent(":irc.server", "001", "Welcome!", "source");
    handler.fireIrcEvent(cmd);
    verify(plugin, never()).parseMsg(cmd);
    verify(plugin, never()).parsePing(cmd);
  }

  @Test
  public void testFireSameIrcEventForEachPlugin() {
    TestPlugin pluginOne = mock(TestPlugin.class);
    TestPlugin pluginTwo = mock(TestPlugin.class);
    handler.addPlugin(pluginOne);
    handler.addPlugin(pluginTwo);
    handler.lookup();

    IrcBaseEvent ping = new IrcBaseEvent("PING", "123", "source");
    handler.fireIrcEvent(ping);
    verify(pluginOne).parsePing(ping);
    verify(pluginTwo).parsePing(ping);
  }

  @Test
  public void testFireIrcEventThrowsInvokationException()
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    TestPlugin pluginOne = mock(TestPlugin.class);
    TestPlugin pluginTwo = mock(TestPlugin.class);
    handler.addPlugin(pluginOne);
    handler.addPlugin(pluginTwo);
    handler.lookup();

    IrcBaseEvent ping = new IrcBaseEvent("PING", "123", "source");
    doThrow(new IllegalAccessException()).when(pluginOne).parseDangerousPing(ping);

    handler.fireIrcEvent(ping);
    verify(pluginOne).parsePing(ping);
    verify(pluginTwo).parsePing(ping);
    verify(pluginTwo).parseDangerousPing(ping);
  }

  @Test
  public void testStartResetsWhenFinished() throws Exception {
    IrcHandler spyHandler = spy(handler);
    spyHandler.setState(new Shutdown());

    spyHandler.start();

    verify(connection).disconnect();
    assertEquals(false, spyHandler.isRunning());
    assertEquals(STARTUP_STATE, spyHandler.getState());
  }

  @Test
  public void testStartFailsWhenFinished() throws Exception {
    IrcHandler spyHandler = spy(handler);
    doThrow(IOException.class).when(connection).disconnect();

    spyHandler.setState(new Shutdown());

    spyHandler.start();

    verify(connection).disconnect();
    assertEquals(false, spyHandler.isRunning());
    assertEquals(STARTUP_STATE, spyHandler.getState());
  }

  @Test
  public void testStopThroughMethodcall() throws InterruptedException {
    LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    handler.setState(testState);
    var thread = new Thread(new TestWorker(handler, countDownLatch));
    thread.start();

    Thread.sleep(10);

    assertEquals(true, handler.isRunning());
    handler.stop();
    countDownLatch.await();
    assertEquals(false, handler.isRunning());
  }

  @Test
  public void testStopThroughLifecycleEnd() throws InterruptedException {
    LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    handler.setState(testState);
    var thread = new Thread(new TestWorker(handler, countDownLatch));
    thread.start();

    Thread.sleep(10);

    assertEquals(true, handler.isRunning());
    handler.setState(new Initialize());
    countDownLatch.await();
    assertEquals(false, handler.isRunning());
  }

  @Test
  public void testStopThroughThreadInterruption() throws InterruptedException {
    final LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    handler.setState(testState);
    var thread = new Thread(new TestWorker(handler, countDownLatch));
    thread.start();

    Thread.sleep(10);

    assertEquals(true, handler.isRunning());
    thread.interrupt();
    countDownLatch.await();
    assertEquals(false, handler.isRunning());
  }

  @Test
  public void testConnectionIsClosed() throws Exception {
    final LifecycleState testState = mock(LifecycleState.class);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    handler.setState(testState);
    var thread = new Thread(new TestWorker(handler, countDownLatch));
    thread.start();

    Thread.sleep(10);

    handler.stop();
    countDownLatch.await();
    verify(connection).disconnect();
  }

  @Test
  public void testDisconnect() throws Exception {
    handler.disconnect();

    verify(connection).disconnect();
  }

  @Test
  public void testReadCommand() throws Exception {
    var command = "PING :1234";
    doReturn(command).when(connection).read();

    var actual = handler.readCommand();

    assertEquals(command, actual);
  }

  @Test
  public void testSendCommandSuccessful() throws Exception {
    handler.sendCommand(new Pong("1234"));

    verify(connection).write("PONG 1234");
  }

  @Test
  public void testSendNullCommand() throws Exception {
    handler.sendCommand(null);

    verify(connection, never()).write(anyString());
  }

  @Test
  public void testSendCommandFail() throws Exception {
    doThrow(IOException.class).when(connection).write(anyString());

    assertDoesNotThrow(() -> handler.sendCommand(new Pong("1234")));
  }

  private class TestWorker implements Runnable {

    private final IrcHandler handler;
    private final CountDownLatch latch;

    public TestWorker(IrcHandler handler, CountDownLatch latch) {
      this.handler = handler;
      this.latch = latch;
    }

    @Override
    public void run() {
      // would run endlessly until stop is called
      handler.start();
      latch.countDown();
    }

  }

  private class TestPlugin implements IrcHandlerPlugin {

    @IrcEventAcceptor(command = "PRIVMSG")
    public void parseMsg(IrcBaseEvent event) {}

    @IrcEventAcceptor(command = "PING")
    public void parsePing(IrcBaseEvent event) {}

    @IrcEventAcceptor(command = "PING")
    public void parseDangerousPing(IrcBaseEvent event)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {}
  }
}

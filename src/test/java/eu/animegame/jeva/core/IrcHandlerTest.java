package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import java.util.Properties;
import org.junit.jupiter.api.Test;

class IrcHandlerTest {

  @Test
  public void testGetConfig() {
    Properties config = new Properties();
    var key = "Key";
    var value = "Value";
    config.put(key, value);

    IrcHandler handler = new IrcHandler(config);
    var handlerConfig = handler.getConfiguration();

    assertEquals(1, handlerConfig.size());
    assertEquals(value, handlerConfig.get(key));
  }

  @Test
  public void testAddPlugin() {
    IrcHandler handler = new IrcHandler(new Properties());
    TestPlugin plugin = mock(TestPlugin.class);

    handler.addPlugin(plugin);
    assertEquals(1, handler.getPlugins().size());

    handler.addPlugin(plugin);
    assertEquals(1, handler.getPlugins().size());
  }

  @Test
  public void testRemovePlugin() {
    IrcHandler handler = new IrcHandler(new Properties());
    TestPlugin plugin = mock(TestPlugin.class);

    handler.addPlugin(plugin);
    assertEquals(1, handler.getPlugins().size());

    handler.removePlugin(new TestPlugin());
    assertEquals(1, handler.getPlugins().size());

    handler.removePlugin(plugin);
    assertEquals(0, handler.getPlugins().size());
  }

  @Test
  public void testGetPluginsIsUnmodifable() {
    IrcHandler handler = new IrcHandler(new Properties());
    TestPlugin plugin = mock(TestPlugin.class);
    handler.addPlugin(plugin);

    var plugins = handler.getPlugins();
    assertEquals(1, plugins.size());
    assertThrows(UnsupportedOperationException.class, () -> plugins.remove(0));
    assertThrows(UnsupportedOperationException.class, () -> plugins.add(plugin));
  }

  @Test
  public void testFireLifecycleEvent() {
    IrcHandler handler = new IrcHandler(new Properties());
    TestPlugin plugin = mock(TestPlugin.class);
    handler.addPlugin(plugin);

    handler.fireLifecycleState(p -> p.connect(handler));
    verify(plugin).connect(handler);
  }

  @Test
  public void testFireIrcEvents() {
    IrcHandler handler = new IrcHandler(new Properties());
    TestPlugin plugin = mock(TestPlugin.class);
    handler.addPlugin(plugin);
    handler.lookup();

    IrcBaseEvent ping = new IrcBaseEvent(null, "PING", "123");
    handler.fireIrcEvent(ping);
    verify(plugin).parsePing(ping);

    IrcBaseEvent privmsg = new IrcBaseEvent(":test.user", "PRIVMSG", "It's a test");
    handler.fireIrcEvent(privmsg);
    verify(plugin).parseMsg(privmsg);

    IrcBaseEvent cmd = new IrcBaseEvent(":irc.server", "001", "Welcome!");
    handler.fireIrcEvent(cmd);
    verify(plugin, never()).parseMsg(cmd);
    verify(plugin, never()).parsePing(cmd);
  }

  @Test
  public void testFireSameIrcEventForEachPlugin() {
    IrcHandler handler = new IrcHandler(new Properties());
    TestPlugin pluginOne = mock(TestPlugin.class);
    TestPlugin pluginTwo = mock(TestPlugin.class);
    handler.addPlugin(pluginOne);
    handler.addPlugin(pluginTwo);
    handler.lookup();

    IrcBaseEvent ping = new IrcBaseEvent(null, "PING", "123");
    handler.fireIrcEvent(ping);
    verify(pluginOne).parsePing(ping);
    verify(pluginTwo).parsePing(ping);
  }


  private class TestPlugin implements IrcHandlerPlugin {

    @IrcEventAcceptor(command = "PRIVMSG")
    public void parseMsg(IrcBaseEvent event) {
    }

    @IrcEventAcceptor(command = "PING")
    public void parsePing(IrcBaseEvent event) {
    }
  }
}

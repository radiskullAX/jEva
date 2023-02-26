package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.irc.events.UserBaseEvent;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class IrcPluginControllerTest {

  private static final String COMMAND_1 = "TEST1";

  private static final String COMMAND_2 = "TEST2";

  private static final String COMMAND_3 = "TEST3";

  private static final String COMMAND_4 = "TEST4";

  private static final String COMMAND_5 = "TEST5";

  private static final String COMMAND_6 = "TEST6";

  private static final String COMMAND_7 = "TEST7";

  private static final String PREFIX = "TestUser!jEva@animegame.eu";

  private static final String PARAMETER = "parameters";

  private static final String SOURCE = "source";

  private IrcPluginController controller;

  private JEvaIrcClient jEvaClient;

  private JEvaIrcPlugin pluginMock1;

  private TestPlugin pluginMock2;

  private IrcPluginControllerTest() {
    jEvaClient = mock(JEvaIrcClient.class);
    controller = new IrcPluginController(jEvaClient);
    pluginMock1 = mock(JEvaIrcPlugin.class);
    pluginMock2 = mock(TestPlugin.class);
  }

  @Test
  void addPlugin() {
    controller.addPlugin(pluginMock1);
    controller.addPlugin(pluginMock2);

    assertEquals(2, controller.getPlugins().size());
  }

  @Test
  void addPluginDuplicates() {
    controller.addPlugin(pluginMock1);
    controller.addPlugin(pluginMock1);

    assertEquals(1, controller.getPlugins().size());
  }

  @Test
  void addPluginWithNullArgument() {
    controller.addPlugin(null);

    assertEquals(0, controller.getPlugins().size());
  }

  @Test
  void removePlugin() {
    controller.addPlugin(pluginMock1);

    assertEquals(1, controller.getPlugins().size());
    var actual = controller.removePlugin(pluginMock1);

    assertEquals(true, actual);
    assertEquals(0, controller.getPlugins().size());
  }

  @Test
  void removePluginWithNullArgument() {
    var actual = controller.removePlugin(null);

    assertEquals(false, actual);
    assertEquals(0, controller.getPlugins().size());
  }

  @Test
  void getPlugin() {
    var testPlugin = new TestPlugin();
    controller.addPlugin(testPlugin);

    var plugin = controller.getPlugin(TestPlugin.class);

    assertEquals(true, plugin.isPresent());
    assertSame(testPlugin, plugin.get());
  }

  @Test
  void getPluginReturnsEmptyOptional() {
    var plugin = controller.getPlugin(TestPlugin.class);

    assertEquals(true, plugin.isEmpty());
  }

  @Test
  void getPlugins() {
    controller.addPlugin(pluginMock1);
    controller.addPlugin(pluginMock2);

    var plugins = controller.getPlugins();

    assertIterableEquals(Arrays.asList(pluginMock1, pluginMock2), plugins);
  }

  @Test
  void getPluginsIsNewUnmodifiableList() {
    controller.addPlugin(pluginMock1);
    controller.addPlugin(pluginMock2);

    var plugins = controller.getPlugins();

    assertEquals(2, plugins.size());
    assertThrows(UnsupportedOperationException.class, () -> plugins.clear());
  }

  @Test
  void fireLifecycleState() {
    controller.addPlugin(pluginMock1);

    controller.fireLifecycleState(p -> p.connect(jEvaClient));
    verify(pluginMock1).connect(jEvaClient);
  }

  @Test
  void fireIrcEvent() {
    var event = new IrcBaseEvent(COMMAND_3, PARAMETER, SOURCE);

    controller.addPlugin(pluginMock2);
    controller.lookup();
    controller.fireIrcEvent(event);

    verify(pluginMock2).method3(event, jEvaClient);
  }

  @Test
  void fireIrcEventForZeroParameterPluginMethod() {
    var event = new IrcBaseEvent(COMMAND_1, PARAMETER, SOURCE);

    controller.addPlugin(pluginMock2);
    controller.lookup();
    controller.fireIrcEvent(event);

    verify(pluginMock2).method1();
  }

  @Test
  void fireIrcEventForOneParameterPluginMethod() {
    var event = new IrcBaseEvent(COMMAND_2, PARAMETER, SOURCE);

    controller.addPlugin(pluginMock2);
    controller.lookup();
    controller.fireIrcEvent(event);

    verify(pluginMock2).method2(event);
  }

  @Test
  void fireIrcEventPluginMethodsNotCalledForWrongCommand() {
    var event = new IrcBaseEvent("COMMAND", PARAMETER, SOURCE);

    controller.addPlugin(pluginMock2);
    controller.lookup();
    controller.fireIrcEvent(event);

    verify(pluginMock2, never()).method1();
    verify(pluginMock2, never()).method2(event);
    verify(pluginMock2, never()).method3(event, jEvaClient);
    verify(pluginMock2, never()).method4(any(UserBaseEvent.class));
  }

  @Test
  void fireIrcEventWithEventSubClass() {
    var event = new UserBaseEvent(new IrcBaseEvent(PREFIX, COMMAND_4, PARAMETER, SOURCE));

    controller.addPlugin(pluginMock2);
    controller.lookup();
    controller.fireIrcEvent(event);

    verify(pluginMock2).method4(event);
  }

  @Test
  void fireIrcEventTransformEventToSubClass() {
    var event = new IrcBaseEvent(PREFIX, COMMAND_4, PARAMETER, SOURCE);

    controller.addPlugin(pluginMock2);
    controller.lookup();
    controller.fireIrcEvent(event);

    verify(pluginMock2).method4(any(UserBaseEvent.class));
  }

  @Test
  void fireIrcEventTransformEventToSubClassThrowsException() throws Exception {
    controller.addPlugin(pluginMock2);
    controller.lookup();

    assertDoesNotThrow(() -> controller.fireIrcEvent(new IrcBaseEvent(COMMAND_6, PARAMETER, SOURCE)));
    verify(pluginMock2, never()).method6(any(TestIrcEvent.class));
  }

  @Test
  void fireIrcEventPluginMethodThrowsException() throws Exception {
    doThrow(new RuntimeException()).when(pluginMock2).method1();
    doThrow(new Exception()).when(pluginMock2).method5();

    controller.addPlugin(pluginMock2);
    controller.lookup();

    assertDoesNotThrow(() -> controller.fireIrcEvent(new IrcBaseEvent(COMMAND_1, PARAMETER, SOURCE)));
    verify(pluginMock2).method1();
    assertDoesNotThrow(() -> controller.fireIrcEvent(new IrcBaseEvent(COMMAND_5, PARAMETER, SOURCE)));
    verify(pluginMock2).method5();
  }

  @Test
  void fireIrcEventPluginMethodHasUnknownParameter() throws Exception {
    controller.addPlugin(pluginMock2);
    controller.lookup();

    assertDoesNotThrow(() -> controller.fireIrcEvent(new IrcBaseEvent(COMMAND_7, PARAMETER, SOURCE)));
    verify(pluginMock2, never()).method7(any(), any(), any());
  }

  @Test
  void fireIrcEventIsSameEventForAllPlugins() {
    IrcBaseEvent event = new IrcBaseEvent(COMMAND_2, PARAMETER, SOURCE);
    var pluginMock3 = mock(TestPlugin.class);

    controller.addPlugin(pluginMock2);
    controller.addPlugin(pluginMock3);
    controller.lookup();
    controller.fireIrcEvent(event);

    assertEquals(2, controller.getPlugins().size());
    verify(pluginMock2).method2(event);
    verify(pluginMock3).method2(event);
  }

  private static class TestIrcEvent extends IrcBaseEvent {

    public TestIrcEvent() {
      super(new IrcBaseEvent("", "", ""));
    }
  }

  private class TestPlugin implements JEvaIrcPlugin {

    @IrcEventAcceptor(command = COMMAND_1)
    public void method1() {}

    @IrcEventAcceptor(command = COMMAND_2)
    public void method2(IrcBaseEvent event) {}

    @IrcEventAcceptor(command = COMMAND_3)
    public void method3(IrcBaseEvent event, JEvaIrcClient jEvaClient) {}

    @IrcEventAcceptor(command = COMMAND_4)
    public void method4(UserBaseEvent event) {}

    @IrcEventAcceptor(command = COMMAND_5)
    public void method5() throws Exception {}

    @IrcEventAcceptor(command = COMMAND_6)
    public void method6(TestIrcEvent event) {}

    @IrcEventAcceptor(command = COMMAND_7)
    public void method7(IrcBaseEvent event, JEvaIrcClient jEvaClient, Object additional) {}
  }
}

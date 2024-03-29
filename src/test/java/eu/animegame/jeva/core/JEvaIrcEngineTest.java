package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import eu.animegame.jeva.Tags;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class JEvaIrcEngineTest {

  private static final String COMMAND = "TestCommand";

  private JEvaIrcEngine jEvaIrcEngine;

  private IrcPluginController pluginController;

  private Connection connection;

  private JEvaIrcEngineTest() {
    connection = mock(Connection.class);
    pluginController = mock(IrcPluginController.class);
    jEvaIrcEngine = new JEvaIrcEngine(connection, pluginController);
  }

  @ParameterizedTest
  @MethodSource("jEvaIrcEngineProvider")
  void testConstructors(JEvaIrcEngine jEvaIrcEngine) {
    assertNotNull(jEvaIrcEngine.getConfig());
    assertNotNull(jEvaIrcEngine.getConnection());
    assertNotNull(jEvaIrcEngine.getPluginController());
  }

  private static Stream<JEvaIrcEngine> jEvaIrcEngineProvider() {
    var connection = mock(Connection.class);
    var pluginController = mock(IrcPluginController.class);
    var ircConfig = mock(IrcConfig.class);
    return Stream.of(new JEvaIrcEngine(), new JEvaIrcEngine(connection), new JEvaIrcEngine(connection, ircConfig),
        new JEvaIrcEngine(connection, pluginController));
  }

  @Test
  void getConfig() {
    // test constructor injection too
    IrcConfig config = jEvaIrcEngine.getConfig();

    assertNotNull(config);
  }

  @Test
  void addPlugin() {
    var plugin = mock(JEvaIrcPlugin.class);
    jEvaIrcEngine.addPlugin(plugin);

    verify(pluginController).addPlugin(plugin);
  }

  @Test
  void removePlugin() {
    var plugin = mock(JEvaIrcPlugin.class);
    jEvaIrcEngine.removePlugin(plugin);

    verify(pluginController).removePlugin(plugin);
  }

  @Test
  void getPlugins() {
    jEvaIrcEngine.getPlugins();

    verify(pluginController).getPlugins();
  }

  @Test
  void getPlugin() {
    var pluginClass = JEvaIrcPlugin.class;
    jEvaIrcEngine.getPlugin(pluginClass);

    verify(pluginController).getPlugin(pluginClass);
  }

  @Test
  void sendCommand() throws Exception {
    var command = mock(IrcCommand.class);
    when(command.build()).thenReturn(COMMAND);

    jEvaIrcEngine.sendCommand(command);

    verify(connection).write(COMMAND);
  }

  @Test
  void sendCommandWithNullArgument() throws Exception {
    jEvaIrcEngine.sendCommand(null);

    verify(connection, never()).write(anyString());
  }

  @Test
  void sendCommandThrowsException() throws Exception {
    var command = mock(IrcCommand.class);
    when(command.build()).thenReturn(COMMAND);
    doThrow(Exception.class).when(connection).write(anyString());

    assertDoesNotThrow(() -> jEvaIrcEngine.sendCommand(command));
  }

  @Test
  void builder() {
    var builder = JEvaIrcEngine.builder();

    assertNotNull(builder);
    assertEquals(JEvaIrcEngineBuilder.class, builder.getClass());
  }
}

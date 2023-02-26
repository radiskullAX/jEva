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
class JEvaIrcClientTest {

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
    assertNotNull(jEvaClient.getConnection());
    assertNotNull(jEvaClient.getPluginController());
  }

  private static Stream<JEvaIrcClient> IrcJEvaClientProvider() {
    var connection = mock(Connection.class);
    var pluginController = mock(IrcPluginController.class);
    var ircConfig = mock(IrcConfig.class);
    return Stream.of(new JEvaIrcClient(), new JEvaIrcClient(connection), new JEvaIrcClient(connection, ircConfig),
        new JEvaIrcClient(connection, pluginController));
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
}

package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.core.exceptions.InitializationException;
import eu.animegame.jeva.core.exceptions.MissingParameterException;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class ConnectPluginTest extends PluginBaseTest<ConnectPlugin> {

  private static final String NICK = IrcConfig.PROP_NICK;

  private static final String PASSWORD = IrcConfig.PROP_SERVER_PASSWORD;

  private static final String MODE = IrcConfig.PROP_MODE;

  private static final String REAL_NAME = IrcConfig.PROP_REAL_NAME;

  private static final String PORT = IrcConfig.PROP_PORT;

  private static final String SERVER = IrcConfig.PROP_SERVER;

  private IrcConfig config;

  public ConnectPluginTest() {
    plugin = new ConnectPlugin();
    config = new IrcConfig();
    jEvaIrcEngine = mock(JEvaIrcEngine.class);
  }

  @BeforeEach
  void before() {
    fillConfig();
    doReturn(config).when(jEvaIrcEngine).getConfig();
  }

  private void fillConfig() {
    config.put(NICK, "TestBot");
    config.put(PASSWORD, "SuperSecret");
    config.put(MODE, "12");
    config.put(REAL_NAME, "TestUser");
    config.put(PORT, "6665");
    config.put(SERVER, "irc.animegame.eu");
  }

  @Test
  void connect() {
    plugin.connect(jEvaIrcEngine);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(jEvaIrcEngine, times(3)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("PASS SuperSecret", commands.get(0).build());
    assertEquals("NICK TestBot", commands.get(1).build());
    assertEquals("USER TestBot 12 * :TestUser", commands.get(2).build());
  }

  @Test
  void connectWithoutPassword() {
    config.remove(PASSWORD);
    plugin.connect(jEvaIrcEngine);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(jEvaIrcEngine, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 12 * :TestUser", commands.get(1).build());
  }

  @Test
  void connectWithStandardValues() {
    config.remove(PASSWORD);
    config.remove(MODE);
    config.remove(REAL_NAME);
    plugin.connect(jEvaIrcEngine);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(jEvaIrcEngine, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 8 * :jEva", commands.get(1).build());
  }

  @Test
  void initialize() {
    assertDoesNotThrow(() -> plugin.initialize(jEvaIrcEngine));
  }

  @ParameterizedTest
  @ValueSource(strings = {NICK, SERVER, PORT})
  void initializeWithNullProperties(String param) {
    config.remove(param);

    Throwable actual = assertThrows(InitializationException.class, () -> plugin.initialize(jEvaIrcEngine));

    assertEquals(MissingParameterException.class, actual.getCause().getClass());
  }

  @ParameterizedTest
  @ValueSource(strings = {NICK, SERVER, PORT})
  void initializeWithEmptyProperties(String param) {
    config.put(param, "");

    Throwable actual = assertThrows(InitializationException.class, () -> plugin.initialize(jEvaIrcEngine));

    assertEquals(MissingParameterException.class, actual.getCause().getClass());
  }
}

package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.exceptions.InitializationException;
import eu.animegame.jeva.core.exceptions.MissingParameterException;

class ConnectPluginTest extends PluginBaseTest<ConnectPlugin> {

  private static final String NICK = IrcConfig.PROP_NICK;

  private static final String PASSWORD = IrcConfig.PROP_PASSWORD;

  private static final String MODE = IrcConfig.PROP_MODE;

  private static final String REAL_NAME = IrcConfig.PROP_REAL_NAME;

  private static final String PORT = IrcConfig.PROP_PORT;

  private static final String SERVER = IrcConfig.PROP_SERVER;

  private IrcConfig config;

  public ConnectPluginTest() {
    plugin = new ConnectPlugin();
    config = new IrcConfig();
    handler = mock(IrcHandler.class);
  }

  @BeforeEach
  void before() {
    buildConfigProperties();
    doReturn(config.getProperties()).when(handler).getConfigProperties();
    doReturn(config).when(handler).getConfig();
  }

  private void buildConfigProperties() {
    var props = getConfigProps();
    props.put(NICK, "TestBot");
    props.put(PASSWORD, "SuperSecret");
    props.put(MODE, "12");
    props.put(REAL_NAME, "TestUser");
    props.put(PORT, "6665");
    props.put(SERVER, "irc.animegame.eu");
  }

  private Properties getConfigProps() {
    return config.getProperties();
  }

  @Test
  void connect() {
    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(3)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("PASS SuperSecret", commands.get(0).build());
    assertEquals("NICK TestBot", commands.get(1).build());
    assertEquals("USER TestBot 12 * :TestUser", commands.get(2).build());
  }

  @Test
  void connectWithoutPassword() {
    getConfigProps().remove(PASSWORD);
    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 12 * :TestUser", commands.get(1).build());
  }

  @Test
  void connectWithStandardValues() {
    getConfigProps().remove(PASSWORD);
    getConfigProps().remove(MODE);
    getConfigProps().remove(REAL_NAME);
    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 8 * :jEva", commands.get(1).build());
  }

  @Test
  void initialize() {
    assertDoesNotThrow(() -> plugin.initialize(handler));
  }

  @ParameterizedTest
  @ValueSource(strings = {NICK, SERVER, PORT})
  void initializeWithNullProperties(String param) {
    getConfigProps().remove(param);

    Throwable actual = assertThrows(InitializationException.class, () -> plugin.initialize(handler));

    assertEquals(MissingParameterException.class, actual.getCause().getClass());
  }

  @ParameterizedTest
  @ValueSource(strings = {NICK, SERVER, PORT})
  void initializeWithEmptyProperties(String param) {
    getConfigProps().put(param, "");

    Throwable actual = assertThrows(InitializationException.class, () -> plugin.initialize(handler));

    assertEquals(MissingParameterException.class, actual.getCause().getClass());
  }
}

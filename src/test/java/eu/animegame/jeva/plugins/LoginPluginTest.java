package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
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
import eu.animegame.jeva.core.IrcHandler;

class LoginPluginTest extends PluginBaseTest<LoginPlugin> {

  private Properties config;

  @BeforeEach
  void before() {
    plugin = new LoginPlugin();
    handler = mock(IrcHandler.class);
    config = buildConfig();
    doReturn(config).when(handler).getConfiguration();
  }

  private Properties buildConfig() {
    var config = new Properties();
    config.put(IrcHandler.PROP_NICK, "TestBot");
    config.put(IrcHandler.PROP_PASSWORD, "SuperSecret");
    config.put(IrcHandler.PROP_MODE, "12");
    config.put(IrcHandler.PROP_REAL_NAME, "TestUser");
    config.put(IrcHandler.PROP_PORT, "6665");
    config.put(IrcHandler.PROP_SERVER, "irc.animegame.eu");
    return config;
  }

  @Test
  void sendSuccessfulAuthWithoutPassword() {
    config.remove(IrcHandler.PROP_PASSWORD);
    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 12 * :TestUser", commands.get(1).build());
  }

  @Test
  void sendSuccessfulAuthWithStandardValues() {
    config.remove(IrcHandler.PROP_PASSWORD);
    config.remove(IrcHandler.PROP_MODE);
    config.remove(IrcHandler.PROP_REAL_NAME);
    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 8 * :jEva", commands.get(1).build());
  }

  @Test
  void sendSuccessfulAuth() {
    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(3)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("PASS SuperSecret", commands.get(0).build());
    assertEquals("NICK TestBot", commands.get(1).build());
    assertEquals("USER TestBot 12 * :TestUser", commands.get(2).build());
  }

  @ParameterizedTest
  @ValueSource(strings = {IrcHandler.PROP_NICK, IrcHandler.PROP_SERVER, IrcHandler.PROP_PORT})
  void throwExceptionForNullConfigParameter(String param) {
    try {
      config.remove(param);
      plugin.initialize(handler);
      fail("Expected Exception was not thrown");
    } catch (RuntimeException e) {
      assertNotNull(e);
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {IrcHandler.PROP_NICK, IrcHandler.PROP_SERVER, IrcHandler.PROP_PORT})
  void throwExceptionForEmptyConfigParameter(String param) {
    try {
      config.put(param, "");
      plugin.initialize(handler);
      fail("Expected Exception was not thrown");
    } catch (RuntimeException e) {
      assertNotNull(e);
    }
  }
}

package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.core.IrcHandler;

class LoginPluginTest {

  LoginPlugin plugin = new LoginPlugin();

  @Test
  void testSendSuccessfulAuthWithoutPassword() {
    IrcHandler handler = mock(IrcHandler.class);
    doReturn(buildConfig()).when(handler).getConfiguration();

    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(2)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("NICK TestBot", commands.get(0).build());
    assertEquals("USER TestBot 8 * :jEva", commands.get(1).build());
  }

  @Test
  void testSendSuccessfulAuth() {
    IrcHandler handler = mock(IrcHandler.class);
    var config = buildConfig();
    config.put(IrcHandler.PROP_PASSWORD, "Secret");
    doReturn(config).when(handler).getConfiguration();

    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(3)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("PASS Secret", commands.get(0).build());
    assertEquals("NICK TestBot", commands.get(1).build());
    assertEquals("USER TestBot 8 * :jEva", commands.get(2).build());
  }

  @Test
  void testSendAuthWithCompleteConfig() {
    var config = new Properties();
    config.put(IrcHandler.PROP_NICK, "JEva");
    config.put(IrcHandler.PROP_PASSWORD, "SuperSecret");
    config.put(IrcHandler.PROP_MODE, "12");
    config.put(IrcHandler.PROP_REAL_NAME, "IrcTestUser");
    IrcHandler handler = mock(IrcHandler.class);
    doReturn(config).when(handler).getConfiguration();

    plugin.connect(handler);

    ArgumentCaptor<IrcCommand> commandCaptor = ArgumentCaptor.forClass(IrcCommand.class);
    verify(handler, times(3)).sendCommand(commandCaptor.capture());

    List<IrcCommand> commands = commandCaptor.getAllValues();
    assertEquals("PASS SuperSecret", commands.get(0).build());
    assertEquals("NICK JEva", commands.get(1).build());
    assertEquals("USER JEva 12 * :IrcTestUser", commands.get(2).build());
  }

  private Properties buildConfig() {
    var config = new Properties();
    config.put(IrcHandler.PROP_NICK, "TestBot");
    return config;
  }
}

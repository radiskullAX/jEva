package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.irc.commands.Quit;

/**
 * 
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class DisconnectPluginTest extends PluginBaseTest<DisconnectPlugin> {

  private static final String QUIT_MESSAGE = "ded!";

  private static final String DEFAULT_QUIT_MESSAGE = "Powered by jEva!";

  private IrcConfig config;

  private DisconnectPluginTest() {
    plugin = new DisconnectPlugin();
    config = new IrcConfig();
    jEvaIrcEngine = mock(JEvaIrcEngine.class);
  }

  @BeforeEach
  void beforeEach() {
    when(jEvaIrcEngine.getConfig()).thenReturn(config);
  }

  @Test
  void initialize() {
    config.put(DisconnectPlugin.PROP_QUIT_MESSAGE, QUIT_MESSAGE);

    plugin.initialize(jEvaIrcEngine);
    
    var result = plugin.getQuitMessage();
    assertEquals(QUIT_MESSAGE, result);
  }

  @Test
  void initializeWithDefaultQuitMessage() {
    plugin.initialize(jEvaIrcEngine);
    
    var result = plugin.getQuitMessage();
    assertEquals(DEFAULT_QUIT_MESSAGE, result);
  }

  @Test
  void disconnect() {
    plugin.initialize(jEvaIrcEngine);
    plugin.disconnect(jEvaIrcEngine);

    ArgumentCaptor<Quit> captor = ArgumentCaptor.forClass(Quit.class);
    verify(jEvaIrcEngine, times(1)).sendCommand(captor.capture());

    Quit quit = captor.getValue();
    var expected = "QUIT :" + DEFAULT_QUIT_MESSAGE;
    assertEquals(expected, quit.build());
  }
}

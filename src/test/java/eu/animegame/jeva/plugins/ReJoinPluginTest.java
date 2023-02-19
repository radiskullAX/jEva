package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.core.exceptions.InitializationException;
import eu.animegame.jeva.core.exceptions.MissingParameterException;
import eu.animegame.jeva.irc.commands.Join;
import eu.animegame.jeva.irc.events.KickEvent;

class ReJoinPluginTest extends PluginBaseTest<ReJoinPlugin> {

  private static final String NICK = IrcConfig.PROP_NICK;

  private static final String USER = "IrcBot";

  private static final String CHANNEL = "#channel";

  private KickEvent event;

  private IrcConfig config;

  private ReJoinPluginTest() {
    plugin = new ReJoinPlugin();
    config = new IrcConfig();
    handler = mock(IrcHandler.class);
    event = mock(KickEvent.class);
  }

  @BeforeEach
  void before() {
    config.put(NICK, USER);
  }

  @Test
  void rejoinChannel() {
    when(handler.getConfig()).thenReturn(config);
    when(event.getKickedUser()).thenReturn(USER);
    when(event.getChannel()).thenReturn(CHANNEL);

    plugin.rejoinChannel(event, handler);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(handler).sendCommand(captor.capture());

    Join join = captor.getValue();
    String expected = "JOIN #channel";
    assertEquals(expected, join.build());
  }

  @Test
  void rejoinChannelWithWrongUser() {
    when(handler.getConfig()).thenReturn(config);
    when(event.getKickedUser()).thenReturn("AnotherUser");
    when(event.getChannel()).thenReturn(CHANNEL);

    plugin.rejoinChannel(event, handler);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(handler, never()).sendCommand(captor.capture());
  }

  @Test
  void rejoinChannelWithNullProperty() {
    config.remove(NICK);
    when(handler.getConfig()).thenReturn(config);
    when(event.getKickedUser()).thenReturn(USER);
    when(event.getChannel()).thenReturn(CHANNEL);

    assertDoesNotThrow(() -> plugin.rejoinChannel(event, handler));
  }

  @Test
  void initialize() {
    when(handler.getConfig()).thenReturn(config);

    assertDoesNotThrow(() -> plugin.initialize(handler));
  }

  @Test
  void initializeThrowsException() {
    config.remove(NICK);
    when(handler.getConfig()).thenReturn(config);
    
    Throwable actual = assertThrows(InitializationException.class, () -> plugin.initialize(handler));
    
    assertEquals(MissingParameterException.class, actual.getCause().getClass());
  }
}

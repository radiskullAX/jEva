package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.JEvaIrcClient;
import eu.animegame.jeva.core.exceptions.InitializationException;
import eu.animegame.jeva.core.exceptions.MissingParameterException;
import eu.animegame.jeva.irc.commands.Join;
import eu.animegame.jeva.irc.events.KickEvent;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class ReJoinPluginTest extends PluginBaseTest<ReJoinPlugin> {

  private static final String NICK = IrcConfig.PROP_NICK;

  private static final String USER = "IrcBot";

  private static final String CHANNEL = "#channel";

  private KickEvent event;

  private IrcConfig config;

  private ReJoinPluginTest() {
    plugin = new ReJoinPlugin();
    config = new IrcConfig();
    jEvaClient = mock(JEvaIrcClient.class);
    event = mock(KickEvent.class);
  }

  @BeforeEach
  void before() {
    config.put(NICK, USER);
  }

  @Test
  void rejoinChannel() {
    when(jEvaClient.getConfig()).thenReturn(config);
    when(event.getKickedUser()).thenReturn(USER);
    when(event.getChannel()).thenReturn(CHANNEL);

    plugin.rejoinChannel(event, jEvaClient);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(jEvaClient).sendCommand(captor.capture());

    Join join = captor.getValue();
    String expected = "JOIN #channel";
    assertEquals(expected, join.build());
  }

  @Test
  void rejoinChannelWithWrongUser() {
    when(jEvaClient.getConfig()).thenReturn(config);
    when(event.getKickedUser()).thenReturn("AnotherUser");
    when(event.getChannel()).thenReturn(CHANNEL);

    plugin.rejoinChannel(event, jEvaClient);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(jEvaClient, never()).sendCommand(captor.capture());
  }

  @Test
  void rejoinChannelWithNullProperty() {
    config.remove(NICK);
    when(jEvaClient.getConfig()).thenReturn(config);
    when(event.getKickedUser()).thenReturn(USER);
    when(event.getChannel()).thenReturn(CHANNEL);

    assertDoesNotThrow(() -> plugin.rejoinChannel(event, jEvaClient));
  }

  @Test
  void initialize() {
    when(jEvaClient.getConfig()).thenReturn(config);

    assertDoesNotThrow(() -> plugin.initialize(jEvaClient));
  }

  @Test
  void initializeThrowsException() {
    config.remove(NICK);
    when(jEvaClient.getConfig()).thenReturn(config);
    
    Throwable actual = assertThrows(InitializationException.class, () -> plugin.initialize(jEvaClient));
    
    assertEquals(MissingParameterException.class, actual.getCause().getClass());
  }
}

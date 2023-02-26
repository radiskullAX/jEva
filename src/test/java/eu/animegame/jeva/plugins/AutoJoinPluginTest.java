package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.IrcConfig;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.irc.commands.Join;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class AutoJoinPluginTest extends PluginBaseTest<AutoJoinPlugin> {

  private static final String CHAN_1 = "#channel1";

  private static final String CHAN_2 = "#channel2";

  private static final String PW_1 = "pw1";

  private static final String PW_2 = "pw2";

  private static final String WS_FILLED = "  ";

  private IrcBaseEvent event;

  private IrcConfig config;

  private AutoJoinPluginTest() {
    plugin = new AutoJoinPlugin();
    config = new IrcConfig();
    jEvaIrcEngine = mock(JEvaIrcEngine.class);
    event = mock(IrcBaseEvent.class);
  }

  @Test
  void addChannel() {
    plugin.addChannel(CHAN_1);
    plugin.addChannel(CHAN_2);

    var result = plugin.getChannels();
    assertEquals(2, result.size());
  }

  @Test
  void addChannelAndPassword() {
    plugin.addChannel(CHAN_1, PW_1);
    plugin.addChannel(CHAN_1, PW_2);

    var result = plugin.getChannels();
    assertEquals(2, result.size());
  }

  @Test
  void addChannelWithInvalidArguments() {
    plugin.addChannel(null);
    plugin.addChannel(WS_FILLED);
    plugin.addChannel("");

    var result = plugin.getChannels();
    assertEquals(0, result.size());
  }

  @Test
  void addChannelAndPasswordWithInvalidArguments() {
    plugin.addChannel(CHAN_1, null);
    plugin.addChannel(null, PW_1);
    plugin.addChannel(CHAN_1, "");
    plugin.addChannel("", PW_1);
    plugin.addChannel(WS_FILLED, PW_1);
    plugin.addChannel(CHAN_1, WS_FILLED);

    var result = plugin.getChannels();
    assertEquals(0, result.size());
  }

  @Test
  void addChannelWithWhitespaces() {
    plugin.addChannel(" " + CHAN_1 + " ");
    plugin.addChannel("  " + CHAN_2 + "  ");

    var result = plugin.getChannels();
    assertArrayEquals(new String[] {CHAN_1, CHAN_2}, result.toArray());
  }

  @Test
  void addChannelAndPasswordWithWhitespaces() {
    plugin.addChannel(" " + CHAN_1 + " ", " " + PW_1 + " ");
    plugin.addChannel("  " + CHAN_2 + "  ", "  " + PW_2 + "  ");

    plugin.sendJoin(event, jEvaIrcEngine);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(jEvaIrcEngine).sendCommand(captor.capture());
    Join join = captor.getValue();
    var expected = "JOIN " + CHAN_1 + "," + CHAN_2 + " " + PW_1 + "," + PW_2;
    assertEquals(expected, join.build());
  }

  @Test
  void addChannelDuplicates() {
    plugin.addChannel(CHAN_1);
    plugin.addChannel(CHAN_1);

    var result = plugin.getChannels();
    assertEquals(1, result.size());
  }

  @Test
  void addChannelAndPasswordDuplicates() {
    plugin.addChannel(CHAN_1, PW_1);
    plugin.addChannel(CHAN_1, PW_1);

    var result = plugin.getChannels();
    assertEquals(1, result.size());
  }

  @Test
  void removeChannel() {
    plugin.addChannel(CHAN_1);

    assertEquals(1, plugin.getChannels().size());

    plugin.removeChannel(CHAN_1);

    assertEquals(0, plugin.getChannels().size());
  }

  @Test
  void removeInvalidChannel() {
    plugin.addChannel("#doNotRemove");

    assertEquals(1, plugin.getChannels().size());

    plugin.removeChannel("#removeMe");

    assertEquals(1, plugin.getChannels().size());

    plugin.removeChannel(null);

    assertEquals(1, plugin.getChannels().size());
  }

  @Test
  void getChannels() {
    plugin.addChannel(CHAN_1);
    plugin.addChannel(CHAN_2);

    var result = plugin.getChannels();
    assertArrayEquals(new String[] {CHAN_1, CHAN_2}, result.toArray());
  }

  @Test
  void getChannelsHasNoPasswords() {
    plugin.addChannel(CHAN_1, PW_1);
    plugin.addChannel(CHAN_2, PW_2);

    var result = plugin.getChannels();
    assertArrayEquals(new String[] {CHAN_1, CHAN_2}, result.toArray());
  }

  @Test
  void initialize() {
    when(jEvaIrcEngine.getConfig()).thenReturn(config);
    config.put(AutoJoinPlugin.PROP_CHANNELS, "#test,#super secret,#channel");

    plugin.initialize(jEvaIrcEngine);
    
    var result = plugin.getChannels();
    assertArrayEquals(new String[] {"#test", "#super", "#channel"}, result.toArray());
  }

  @Test
  void initializeWithWhitespaces() {
    when(jEvaIrcEngine.getConfig()).thenReturn(config);
    config.put(AutoJoinPlugin.PROP_CHANNELS, "  #test1 , #test2,  #test3  ,#test4   pw,#test5  ");

    plugin.initialize(jEvaIrcEngine);
    
    var result = plugin.getChannels();
    assertArrayEquals(new String[] {"#test1", "#test2", "#test3", "#test4", "#test5"}, result.toArray());
  }

  @Test
  void initializeWithNullProperty() {
    when(jEvaIrcEngine.getConfig()).thenReturn(config);

    assertDoesNotThrow(() -> plugin.initialize(jEvaIrcEngine));
    
    var result = plugin.getChannels();
    assertEquals(0, result.size());
  }

  @Test
  void sendJoin() {
    plugin.addChannel(CHAN_1);
    plugin.addChannel(CHAN_2);

    plugin.sendJoin(event, jEvaIrcEngine);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(jEvaIrcEngine, times(1)).sendCommand(captor.capture());

    Join join = captor.getValue();
    var expected = "JOIN " + CHAN_1 + "," + CHAN_2;
    assertEquals(expected, join.build());
  }

  @Test
  void sendJoinWithPassword() {
    plugin.addChannel(CHAN_1, PW_1);
    plugin.addChannel(CHAN_2, PW_2);

    plugin.sendJoin(event, jEvaIrcEngine);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(jEvaIrcEngine, times(1)).sendCommand(captor.capture());

    Join join = captor.getValue();
    var expected = "JOIN " + CHAN_1 + "," + CHAN_2 + " " + PW_1 + "," + PW_2;
    assertEquals(expected, join.build());
  }

  @Test
  void sendJoinMixedChannels() {
    plugin.addChannel(CHAN_1);
    plugin.addChannel(CHAN_2, PW_2);

    plugin.sendJoin(event, jEvaIrcEngine);

    ArgumentCaptor<Join> captor = ArgumentCaptor.forClass(Join.class);
    verify(jEvaIrcEngine, times(2)).sendCommand(captor.capture());
  }
}

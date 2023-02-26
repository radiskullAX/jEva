package eu.animegame.jeva.plugins;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.irc.events.PrivMsgEvent;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class QuickQuitPluginTest extends PluginBaseTest<QuickQuitPlugin> {

  private static final String USER = "IrcUser";

  private PrivMsgEvent event;

  private QuickQuitPluginTest() {
    plugin = new QuickQuitPlugin();
    jEvaIrcEngine = mock(JEvaIrcEngine.class);
    event = mock(PrivMsgEvent.class);
  }

  @Test
  void parseInput() {
    when(event.getMessage()).thenReturn("!quit");
    when(event.getNickname()).thenReturn(USER);
    
    plugin.parseInput(event, jEvaIrcEngine);
    
    verify(jEvaIrcEngine).stop();
  }

  @Test
  void parseInputIgnoresCase() {
    when(event.getMessage()).thenReturn("!Quit");
    when(event.getNickname()).thenReturn(USER);
    
    plugin.parseInput(event, jEvaIrcEngine);
    
    verify(jEvaIrcEngine).stop();
  }

  @Test
  void parseInputWithWrongMessage() {
    when(event.getMessage()).thenReturn("Hey, this is a test!");
    
    plugin.parseInput(event, jEvaIrcEngine);
    
    verify(jEvaIrcEngine, never()).stop();
  }

}

package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.irc.events.PrivMsgEvent;

class QuickQuitPluginTest extends PluginBaseTest<QuickQuitPlugin> {

  private static final String USER = "IrcUser";

  private PrivMsgEvent event;

  private QuickQuitPluginTest() {
    plugin = new QuickQuitPlugin();
    handler = mock(IrcHandler.class);
    event = mock(PrivMsgEvent.class);
  }

  @Test
  void parseInput() {
    when(event.getMessage()).thenReturn("!quit");
    when(event.getNickname()).thenReturn(USER);
    
    assertThrows(Exception.class, () -> plugin.parseInput(event, handler));
  }

  @Test
  void parseInputIgnoresCase() {
    when(event.getMessage()).thenReturn("!Quit");
    when(event.getNickname()).thenReturn(USER);
    
    assertThrows(Exception.class, () -> plugin.parseInput(event, handler));
  }

  @Test
  void parseInputWithWrongMessage() {
    when(event.getMessage()).thenReturn("Hey, this is a test!");
    
    assertDoesNotThrow(() -> plugin.parseInput(event, handler));
  }

}

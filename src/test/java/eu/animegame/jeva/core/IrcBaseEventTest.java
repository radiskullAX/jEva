package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 *
 * @author radiskull
 */
class IrcBaseEventTest {

  private static final String PREFIX = "jEva!~Tester@animegame.eu";

  private static final String COMMAND = "PRIVMSG";

  private static final String PARAMETER = "#jEva :Heya! I am a bot.";

  private static final String SOURCE = PREFIX + " " + COMMAND + " " + PARAMETER;

  @Test
  void testConstructors() {
    IrcBaseEvent event = new IrcBaseEvent(PREFIX, COMMAND, PARAMETER, SOURCE);
    assertFields(event);

    IrcBaseEvent copy = new IrcBaseEvent(event);
    assertFields(copy);

    IrcBaseEvent noPrefix = new IrcBaseEvent(COMMAND, PARAMETER, SOURCE);
    assertEquals(Optional.empty(), noPrefix.getPrefix());
    assertEquals(COMMAND, noPrefix.getCommand());
    assertEquals(PARAMETER, noPrefix.getParameters());
    assertEquals(SOURCE, noPrefix.getSource());
  }

  private void assertFields(IrcBaseEvent event) {
    assertEquals(PREFIX, event.getPrefix().get());
    assertEquals(COMMAND, event.getCommand());
    assertEquals(PARAMETER, event.getParameters());
    assertEquals(SOURCE, event.getSource());
  }

}

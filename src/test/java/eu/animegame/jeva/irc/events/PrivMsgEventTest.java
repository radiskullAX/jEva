package eu.animegame.jeva.irc.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcBaseEvent;

class PrivMsgEventTest {

  private static final String PREFIX = "jEva!~Tester@animegame.eu";

  private static final String COMMAND = "PRIVMSG";

  private static final String CHANNEL = "#jEva";

  private static final String MESSAGE = "Heya! I am a bot.";

  private static final String PARAMETER = CHANNEL + " :" + MESSAGE;

  private static final String SOURCE = PREFIX + " " + COMMAND + " " + PARAMETER;


  @Test
  public void testEventParsing() {
    IrcBaseEvent baseEvent = new IrcBaseEvent(PREFIX, COMMAND, PARAMETER, SOURCE);
    PrivMsgEvent event = new PrivMsgEvent(baseEvent);

    assertEquals(CHANNEL, event.getChannel());
    assertEquals(MESSAGE, event.getMessage());
  }
}

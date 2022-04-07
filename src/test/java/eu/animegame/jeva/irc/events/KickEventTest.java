package eu.animegame.jeva.irc.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcBaseEvent;

class KickEventTest {

  private static final String PREFIX = "jEva!~Tester@animegame.eu";

  private static final String COMMAND = "KICK";

  private static final String CHANNEL = "#jEva";

  private static final String USER = "Tester1";

  private static final String MESSAGE = "Do your testing!";

  private static final String PARAMETER = CHANNEL + " " + USER;

  private static final String SOURCE = PREFIX + " " + COMMAND + " " + PARAMETER;


  @Test
  public void testEventParsing() {
    IrcBaseEvent baseEvent = new IrcBaseEvent(PREFIX, COMMAND, PARAMETER, SOURCE);
    KickEvent event = new KickEvent(baseEvent);

    assertEquals(CHANNEL, event.getChannel());
    assertEquals(USER, event.getKickedUser());
    assertEquals(Optional.empty(), event.getMessage());

    var newParameters = PARAMETER + " :" + MESSAGE;
    var newSource = PREFIX + " " + COMMAND + " " + newParameters;
    baseEvent = new IrcBaseEvent(PREFIX, COMMAND, newParameters, newSource);
    event = new KickEvent(baseEvent);

    assertEquals(CHANNEL, event.getChannel());
    assertEquals(USER, event.getKickedUser());
    assertEquals(MESSAGE, event.getMessage().get());
  }

}

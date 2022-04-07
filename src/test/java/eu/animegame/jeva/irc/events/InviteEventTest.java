package eu.animegame.jeva.irc.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import eu.animegame.jeva.core.IrcBaseEvent;

class InviteEventTest {

  private static final String PREFIX = "jEva!~Tester@animegame.eu";

  private static final String COMMAND = "INVITE";

  private static final String CHANNEL = "#jEva";

  private static final String USER = "Tester1";

  private static final String PARAMETER = USER + " " + CHANNEL;

  private static final String SOURCE = PREFIX + " " + COMMAND + " " + PARAMETER;


  @Test
  public void testEventParsing() {
    IrcBaseEvent baseEvent = new IrcBaseEvent(PREFIX, COMMAND, PARAMETER, SOURCE);
    InviteEvent event = new InviteEvent(baseEvent);

    assertEquals(USER, event.getInvitedUser());
    assertEquals(CHANNEL, event.getChannel());
  }

}

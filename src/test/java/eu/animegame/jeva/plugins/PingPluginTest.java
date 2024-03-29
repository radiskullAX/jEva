package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.Tags;
import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.JEvaIrcEngine;
import eu.animegame.jeva.irc.commands.Pong;

/**
 *
 * @author radiskull
 */
@Tag(Tags.UNIT)
class PingPluginTest extends PluginBaseTest<PingPlugin> {

  private PingPluginTest() {
    plugin = new PingPlugin();
    jEvaIrcEngine = mock(JEvaIrcEngine.class);
  }

  @Test
  void successfulPong() {
    IrcBaseEvent event = new IrcBaseEvent("PING", "1234", "source");
    plugin.sendPong(event, jEvaIrcEngine);

    ArgumentCaptor<Pong> captor = ArgumentCaptor.forClass(Pong.class);
    verify(jEvaIrcEngine).sendCommand(captor.capture());

    Pong pong = captor.getValue();
    assertEquals("PONG 1234", pong.build());
  }
}

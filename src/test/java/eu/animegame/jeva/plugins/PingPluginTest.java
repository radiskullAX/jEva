package eu.animegame.jeva.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.IrcHandler;
import eu.animegame.jeva.irc.commands.Pong;

class PingPluginTest {

  PingPlugin plugin = new PingPlugin();

  @Test
  void test() {
    IrcBaseEvent event = new IrcBaseEvent("PING", "1234", "source");
    IrcHandler handler = mock(IrcHandler.class);

    plugin.sendPong(event, handler);

    ArgumentCaptor<Pong> captor = ArgumentCaptor.forClass(Pong.class);
    verify(handler).sendCommand(captor.capture());

    Pong pong = captor.getValue();
    assertEquals("PONG 1234", pong.build());
  }

}

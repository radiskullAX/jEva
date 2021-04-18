package eu.animegame.jeva.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import eu.animegame.jeva.core.exceptions.JEvaException;
import eu.animegame.jeva.core.exceptions.UnknownFormatException;

/**
 *
 * @author radiskull
 */
class IrcCommandParserTest {

  private final static String MESSAGE = "This is a Testmessage";

  private final static String SERVER = "irc.testserver.eu";

  private final static String COMMAND = "PRIVMSG";

  @Test
  void testParseNoPrefix() throws JEvaException, UnknownFormatException {
    var command = "PING";
    var parameter = " :" + MESSAGE;
    var message = buildMessage(null, command, parameter);

    var ircEvent = IrcCommandParser.toIrcEvent(message);

    assertEquals(ircEvent.getPrefix(), Optional.empty());
    assertEquals(ircEvent.getCommand(), command);
    assertEquals(ircEvent.getParameters(), parameter);
    assertEquals(ircEvent.getSource(), message);
  }

  @ParameterizedTest
  @ValueSource(strings = {"001", "NOTICE"})
  void testParseCommands(String command) throws JEvaException, UnknownFormatException {
    var message = buildMessage(SERVER, command, MESSAGE);

    var ircEvent = IrcCommandParser.toIrcEvent(message);

    assertEquals(ircEvent.getPrefix().get(), SERVER);
    assertEquals(ircEvent.getCommand(), command);
    assertEquals(ircEvent.getParameters(), MESSAGE);
    assertEquals(ircEvent.getSource(), message);
  }

  @ParameterizedTest
  @ValueSource(strings = {"irc.server.org", "128.0.0.1", "IrcTestBot!~Tester@animegame.eu", "IrcTestBot@animegame.eu",
      "IrcTestBot!~Tester@128.0.0.1"})
  void testParsePrefix(String prefix) throws JEvaException, UnknownFormatException {
    var message = buildMessage(prefix, COMMAND, MESSAGE);

    var ircEvent = IrcCommandParser.toIrcEvent(message);

    assertEquals(ircEvent.getPrefix().get(), prefix);
    assertEquals(ircEvent.getCommand(), COMMAND);
    assertEquals(ircEvent.getParameters(), MESSAGE);
    assertEquals(ircEvent.getSource(), message);
  }

  @ParameterizedTest
  @ValueSource(strings = {":231951231", ":PONG", "Testuser +i", "AUTH :*** Test", "#test :This is a test",
      "Just some sample text"})
  void testParseParameter(String parameter) throws JEvaException, UnknownFormatException {
    var message = buildMessage(SERVER, COMMAND, parameter);

    var ircEvent = IrcCommandParser.toIrcEvent(message);

    assertEquals(ircEvent.getPrefix().get(), SERVER);
    assertEquals(ircEvent.getCommand(), COMMAND);
    assertEquals(ircEvent.getParameters(), parameter);
    assertEquals(ircEvent.getSource(), message);
  }

  @Test
  void testParseNoMessage() {
    Assertions.assertThrows(JEvaException.class, () -> IrcCommandParser.toIrcEvent(null));
  }

  @Test
  void testParseMessageWithUnknownFormat() {
    var message = "Yolo!";
    var unknownMessage =
        Assertions.assertThrows(UnknownFormatException.class, () -> IrcCommandParser.toIrcEvent(message))
            .getUnknownMessage();
    assertEquals(message, unknownMessage);
  }

  private String buildMessage(String prefix, String command, String parameters) {
    var message = new StringBuilder();
    if (prefix != null) {
      message.append(":").append(prefix).append(" ");
    }
    message.append(command).append(" ").append(parameters);
    return message.toString();
  }
}
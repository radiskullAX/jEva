package eu.animegame.jeva.core;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author radiskull
 */
class IrcMessageParser {

  /**
   * This pattern should match to all messages received from a server. Each group has a name, explained below.<br>
   * <dl>
   * <dt>prefix</dt>
   * <dd>This can be optional. If it exist it will always start with ':' followed by the sender of the message.</dd>
   * <dt>command</dt>
   * <dd>The irc command, either numeric (max length of 3) or alphabetical.</dd>
   * <dt>parameters</dt>
   * <dd>Addentional information for commands.</dd>
   * </dl>
   */
  private static final Pattern IRC_MESSAGE_PATTERN =
      Pattern.compile(
          "(?>:(?<prefix>[\\S]+)\\s)?(?<command>\\w+)\\s(?<parameters>.*)");

  public BasicIrcEvent toIrcEvent(String message) throws IOException, UnknownFormatException {
    if (message == null) {
      throw new IOException("Cannot parse null.");
    }

    Matcher matcher = IRC_MESSAGE_PATTERN.matcher(message);
    if (!matcher.matches()) {
      var exception = new UnknownFormatException("Could not match with irc format");
      exception.setUnknownMessage(message);
      throw exception;
    }

    var prefix = matcher.group("prefix");
    var command = matcher.group("command");
    var parameters = matcher.group("parameters");
    return new BasicIrcEvent(prefix, command, parameters);
  }
}

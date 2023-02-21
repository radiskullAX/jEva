package eu.animegame.jeva.irc.events;

import java.util.Optional;
import eu.animegame.jeva.core.IrcBaseEvent;

/**
 *
 * @author radiskull
 */
public class KickEvent extends UserBaseEvent {

  private final String channel;

  private final String kickedUser;

  private final Optional<String> message;

  public KickEvent(IrcBaseEvent event) {
    super(event);
    var parameters = event.getParameters();
    var startOfTrail = parameters.indexOf(":");
    var indexOfSpace = parameters.indexOf(0x20);
    channel = parameters.substring(0, indexOfSpace);
    if (startOfTrail == -1) {
      kickedUser = parameters.substring(indexOfSpace + 1);
      message = Optional.empty();
    } else {
      kickedUser = parameters.substring(indexOfSpace + 1, startOfTrail - 1);
      message = Optional.of(parameters.substring(startOfTrail + 1));
    }
  }

  public String getChannel() {
    return channel;
  }

  public String getKickedUser() {
    return kickedUser;
  }

  public Optional<String> getMessage() {
    return message;
  }

}

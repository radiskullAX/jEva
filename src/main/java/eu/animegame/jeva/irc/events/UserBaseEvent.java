package eu.animegame.jeva.irc.events;

import eu.animegame.jeva.core.IrcBaseEvent;
import eu.animegame.jeva.core.exceptions.InitializationException;

/**
 *
 * @author radiskull
 */
public class UserBaseEvent extends IrcBaseEvent {

  private final String nickname;

  private final String user;

  private final String host;

  public UserBaseEvent(IrcBaseEvent event) {
    super(event);
    if (event.getPrefix().isPresent()) {
      var prefix = event.getPrefix().get();
      var endOfNick = prefix.indexOf("!");
      var endOfUser = prefix.indexOf("@");
      nickname = prefix.substring(0, endOfNick);
      user = prefix.substring(endOfNick + 1, endOfUser);
      host = prefix.substring(endOfUser + 1);
    } else {
      throw new InitializationException("Cannot transform event: empty prefix!");
    }
  }

  public String getNickname() {
    return nickname;
  }

  public String getUser() {
    return user;
  }

  public String getHost() {
    return host;
  }

}

package eu.animegame.jeva.irc.events;

import eu.animegame.jeva.core.IrcBaseEvent;

public class PrivMsgEvent extends UserBaseEvent {

  private final String channel;

  private final String message;

  public PrivMsgEvent(IrcBaseEvent event) {
    super(event);
    var parameters = event.getParameters();
    var index = parameters.indexOf(0x20);
    channel = parameters.substring(0, index);
    message = parameters.substring(index + 2);
  }


  public String getChannel() {
    return channel;
  }

  public String getMessage() {
    return message;
  }
}

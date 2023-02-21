package eu.animegame.jeva.irc.events;

import eu.animegame.jeva.core.IrcBaseEvent;

/**
 *
 * @author radiskull
 */
public class InviteEvent extends UserBaseEvent {

  private final String invitedUser;

  private final String channel;

  public InviteEvent(IrcBaseEvent event) {
    super(event);
    var params = event.getParameters();
    var endOfUser = params.indexOf(0x20);
    invitedUser = params.substring(0, endOfUser);
    channel = params.substring(endOfUser + 1);
  }

  public String getInvitedUser() {
    return invitedUser;
  }

  public String getChannel() {
    return channel;
  }
}

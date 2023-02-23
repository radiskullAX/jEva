package eu.animegame.jeva.irc.commands;

import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.irc.ModeSetting;

/**
 *
 * @author radiskull
 */
public final class UserMode implements IrcCommand {

  public static final String COMMAND = "MODE";

  private final String nick;

  private final String mode;

  private final ModeSetting setting;

  /**
   * get a list of modes set for given user
   * 
   * @param nick
   */
  public UserMode(String nick) {
    this(nick, null, null);
  }

  /**
   * i - marks a users as invisible<br>
   * w - user receives wallops<br>
   * o - operator flag<br>
   * O - local operator flag<br>
   * r - restricted user connection
   * 
   * @see <a href="https://tools.ietf.org/html/rfc2812#section-3.1.5">RFC 2812 s 3.1.5</a>
   */
  public UserMode(String nick, String mode, ModeSetting setting) {
    this.nick = nick;
    this.mode = mode;
    this.setting = setting;
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    if (mode != null && setting != null) {
      command.append(" ").append(setting).append(mode);
    }
    return command.toString();
  }

}

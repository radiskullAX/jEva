package eu.animegame.jeva.irc.commands;

import static eu.animegame.jeva.irc.ModeSetting.NONE;
import eu.animegame.jeva.core.IrcCommand;
import eu.animegame.jeva.irc.ModeSetting;

/**
 * i - marks a users as invisible<br>
 * w - user receives wallops<br>
 * o - operator flag<br>
 * O - local operator flag<br>
 * r - restricted user connection
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2812#section-3.1.5">RFC 2812 s 3.1.5</a>
 *
 *
 * @author radiskull
 */
public record UserMode(String nick, String mode, ModeSetting setting) implements IrcCommand {

  public static final String COMMAND = "MODE";

  /**
   * get a list of modes set for given user
   * 
   * @param nick
   */
  public UserMode(String nick) {
    this(nick, "", NONE);
  }

  @Override
  public String build() {
    var command = new StringBuilder(COMMAND);
    command.append(" ").append(nick);
    if (!mode.isBlank()) {
      command.append(" ").append(setting).append(mode);
    }
    return command.toString();
  }
}

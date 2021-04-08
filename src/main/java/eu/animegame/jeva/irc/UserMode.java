package eu.animegame.jeva.irc;

/**
 * This enum defines all usable user modes specified in RFC 2812.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2812#section-3.1.5">RFC 2812 s 3.1.5</a>
 * @author radiskull
 */
public enum UserMode {

  /**
   * i - marks a users as invisible
   */
  i,
  /**
   * w - user receives wallops
   */
  w,
  /**
   * o - operator flag
   */
  o,
  /**
   * O - local operator flag
   */
  O,
  /**
   * r - restricted user connection
   */
  r;
}

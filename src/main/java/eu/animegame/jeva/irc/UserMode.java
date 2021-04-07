package eu.animegame.jeva.irc;

/**
 * This class contains all usable user modes specified in RFC 2812.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2812#section-3.1.5">RFC 2812 s 3.1.5</a>
 * @author radiskull
 */
public class UserMode {

  /**
   * i - marks a users as invisible
   */
  public static final String ADD_i = "+i";

  /**
   * i - marks a users as invisible
   */
  public static final String REMOVE_i = "-i";

  /**
   * w - user receives wallops
   */
  public static final String ADD_W = "+w";

  /**
   * w - user receives wallops
   */
  public static final String REMOVE_W = "-w";

  /**
   * o - operator flag
   */
  public static final String ADD_o = "+o";

  /**
   * o - operator flag
   */
  public static final String REMOVE_o = "-o";

  /**
   * O - local operator flag
   */
  public static final String ADD_O = "+O";

  /**
   * O - local operator flag
   */
  public static final String REMOVE_O = "-O";

  /**
   * r - restricted user connection
   */
  public static final String ADD_r = "+r";

  /**
   * r - restricted user connection
   */
  public static final String REMOVE_r = "-r";
}

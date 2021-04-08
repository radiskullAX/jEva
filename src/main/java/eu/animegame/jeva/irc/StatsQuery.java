package eu.animegame.jeva.irc;

/**
 * This enum defines all usable stat queries specified in RFC 2812.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2812#section-3.4.4">RFC 2812 s 3.4.4</a>
 * @author radiskull
 */
public enum StatsQuery {
  /**
   * l - returns a list of the server's connections, showing how long each connection has been established and the
   * traffic over that connection in Kbytes and messages for each direction
   */
  l,
  /**
   * m - returns the usage count for each of commands supported by the server; commands for which the usage count is
   * zero MAY be omitted
   */
  m,
  /**
   * o - returns a list of configured privileged users, operators
   */
  o,
  /**
   * u - returns a string showing how long the server has been up
   */
  u;
}

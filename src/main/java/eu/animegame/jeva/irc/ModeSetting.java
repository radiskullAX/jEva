package eu.animegame.jeva.irc;

/**
 * This enum defines all usable user modes specified in RFC 2812.
 * 
 * @see <a href="https://tools.ietf.org/html/rfc2812#section-3.1.5">RFC 2812 s 3.1.5</a>
 * @author radiskull
 */
public enum ModeSetting {

  ADD("+"),
  REMOVE("-");

  private String value;

  private ModeSetting(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  // TODO: overwrite toString() and return the right value
}

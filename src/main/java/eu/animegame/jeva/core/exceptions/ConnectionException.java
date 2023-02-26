package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class ConnectionException extends JEvaException {

  private static final long serialVersionUID = 9177230105846225021L;

  public ConnectionException() {
  }

  public ConnectionException(String message) {
    super(message);
  }

  public ConnectionException(Throwable cause) {
    super(cause);
  }

  public ConnectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

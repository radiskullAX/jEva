package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class MissingParameterException extends JEvaException {

  private static final long serialVersionUID = -7199874996936294178L;

  public MissingParameterException() {
  }

  public MissingParameterException(String message) {
    super(message);
  }

  public MissingParameterException(Throwable cause) {
    super(cause);
  }

  public MissingParameterException(String message, Throwable cause) {
    super(message, cause);
  }

  public MissingParameterException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

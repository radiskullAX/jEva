package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class InitializationException extends RuntimeException {

  private static final long serialVersionUID = 1397840744847369170L;

  public InitializationException() {
  }

  public InitializationException(String message) {
    super(message);
  }

  public InitializationException(Throwable cause) {
    super(cause);
  }

  public InitializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public InitializationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

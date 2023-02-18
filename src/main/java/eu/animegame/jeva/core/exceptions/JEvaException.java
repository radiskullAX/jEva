package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class JEvaException extends Exception {

  private static final long serialVersionUID = -8288612328564448007L;

  public JEvaException() {
  }

  public JEvaException(String message) {
    super(message);
  }

  public JEvaException(Throwable cause) {
    super(cause);
  }

  public JEvaException(String message, Throwable cause) {
    super(message, cause);
  }

  public JEvaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

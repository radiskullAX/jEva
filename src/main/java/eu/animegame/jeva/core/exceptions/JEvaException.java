package eu.animegame.jeva.core.exceptions;

public class JEvaException extends Exception {

  private static final long serialVersionUID = 1L;

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

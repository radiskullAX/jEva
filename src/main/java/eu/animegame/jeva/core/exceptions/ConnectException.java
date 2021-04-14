package eu.animegame.jeva.core.exceptions;

public class ConnectException extends JEvaException {

  private static final long serialVersionUID = 1L;

  public ConnectException() {
  }

  public ConnectException(String message) {
    super(message);
  }

  public ConnectException(Throwable cause) {
    super(cause);
  }

  public ConnectException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

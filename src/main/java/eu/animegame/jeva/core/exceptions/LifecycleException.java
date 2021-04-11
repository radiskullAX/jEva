package eu.animegame.jeva.core.exceptions;

public class LifecycleException extends Exception {

  private static final long serialVersionUID = 1L;

  public LifecycleException() {
  }

  public LifecycleException(String message) {
    super(message);
  }

  public LifecycleException(Throwable cause) {
    super(cause);
  }

  public LifecycleException(String message, Throwable cause) {
    super(message, cause);
  }

  public LifecycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

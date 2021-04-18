package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class LifecycleException extends JEvaException {

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

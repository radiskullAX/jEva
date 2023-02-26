package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class LifeCycleException extends JEvaException {

  private static final long serialVersionUID = -638942773118698269L;

  public LifeCycleException() {
  }

  public LifeCycleException(String message) {
    super(message);
  }

  public LifeCycleException(Throwable cause) {
    super(cause);
  }

  public LifeCycleException(String message, Throwable cause) {
    super(message, cause);
  }

  public LifeCycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}

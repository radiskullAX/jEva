package eu.animegame.jeva.core.exceptions;

/**
 *
 * @author radiskull
 */
public class UnknownFormatException extends JEvaException {

  private static final long serialVersionUID = -2112833639249017362L;

  private String unknownMessage;

  public UnknownFormatException() {
  }

  public UnknownFormatException(String message) {
    super(message);
  }

  public UnknownFormatException(Throwable cause) {
    super(cause);
  }

  public UnknownFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnknownFormatException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public String getUnknownMessage() {
    return unknownMessage;
  }

  public void setUnknownMessage(String unknownMessage) {
    this.unknownMessage = unknownMessage;
  }

}

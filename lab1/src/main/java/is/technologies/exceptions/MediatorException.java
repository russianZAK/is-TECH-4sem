package is.technologies.exceptions;

/**
 An exception class representing errors that may occur in the Mediator class.
 */
public class MediatorException extends Exception{
  private MediatorException(String message) {}

  public static MediatorException invalidClient() {
    return new MediatorException("client is invalid");
  }

  public static MediatorException invalidNotification() {
    return new MediatorException("notification is invalid");
  }
}

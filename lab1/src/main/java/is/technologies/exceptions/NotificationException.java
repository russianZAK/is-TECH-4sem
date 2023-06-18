package is.technologies.exceptions;

/**
 An exception class representing errors that may occur in the Notification class.
 */
public class NotificationException extends Exception{
  private NotificationException(String message) {}

  public static NotificationException invalidMessage() {
    return new NotificationException("message is invalid");
  }
}

package is.technologies.notifications;

/**
 Interface representing a notification that can be sent to observers.
 */
public interface Notification {
  /**
  Returns the message associated with the notification.
  @return the message associated with the notification.
  */
  String getMessage();
}

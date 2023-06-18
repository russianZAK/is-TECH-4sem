package is.technologies.notifications;

import is.technologies.exceptions.NotificationException;
import lombok.Getter;

/**
 Represents a notification for changing credit limit for a credit account.
 */
@Getter
public class ChangeCreditLimitNotification implements Notification {

  private final String message;

  /**
   Constructs a new notification with the specified message.
   @param message the message for the notification.
   @throws NotificationException if the message is null.
   */
  public ChangeCreditLimitNotification(String message) throws NotificationException {
    if (message == null)
      throw NotificationException.invalidMessage();
    this.message = message;
  }
}

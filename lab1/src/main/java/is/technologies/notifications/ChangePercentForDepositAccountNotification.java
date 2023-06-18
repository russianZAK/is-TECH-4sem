package is.technologies.notifications;

import is.technologies.exceptions.*;
import lombok.Getter;

/**
 Represents a notification for changing percents for a deposit account.
 */
@Getter
public class ChangePercentForDepositAccountNotification implements Notification {

  private final String message;

  /**
   Constructs a new notification with the specified message.
   @param message the message for the notification.
   @throws NotificationException if the message is null.
   */
  public ChangePercentForDepositAccountNotification(String message) throws NotificationException {
    if (message == null)
      throw NotificationException.invalidMessage();
    this.message = message;
  }
}

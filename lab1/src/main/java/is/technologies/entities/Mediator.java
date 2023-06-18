package is.technologies.entities;

import is.technologies.exceptions.*;
import is.technologies.models.*;
import is.technologies.notifications.*;

/**
 Represents a mediator interface.
 */
public interface Mediator {
  /**
   Notifies the specified client about the specified notification.
   @param client The client to notify.
   @param notification The notification to send to the client.
   @throws MediatorException if there is an error while mediating the notification.
   @throws ClientException if there is an error with the client.
   */
  void notify(Client client, Notification notification) throws MediatorException, ClientException;
}

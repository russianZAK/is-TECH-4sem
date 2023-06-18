package is.technologies.entities;

import is.technologies.exceptions.*;

/**
 Represents an observer interface.
 @param <TPayload> The type of the payload to observe.
 */
public interface Observer<TPayload>{
  /**
   Updates the observer with the specified payload.
   @param payload The payload to update the observer with.
   @throws MediatorException if there is an error while mediating the update.
   @throws ClientException if there is an error with the client.
   */
  void update(TPayload payload) throws MediatorException, ClientException;
}

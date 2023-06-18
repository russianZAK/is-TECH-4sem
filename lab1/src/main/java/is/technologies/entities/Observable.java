package is.technologies.entities;

import is.technologies.exceptions.*;

/**
 Represents an observable interface.
 @param <TPayload> The type of the payload to observe.
 */
public interface Observable<TPayload> {
  /**
   Subscribes the specified observer to this observable.
   @param observer The observer to subscribe.
   @throws AggregatorException if there is an error while aggregating the subscriptions.
   */
  void subscribe(Observer<TPayload> observer) throws AggregatorException;

  /**
   Unsubscribes the specified observer from this observable.
   @param observer The observer to unsubscribe.
   @throws AggregatorException if there is an error while aggregating the unsubscriptions.
   */
  void unsubscribe(Observer<TPayload> observer) throws AggregatorException;

  /**
   Notifies all observers subscribed to this observable with the specified payload.
   @param payload The payload to notify the observers with.
   @throws AggregatorException if there is an error while aggregating the notifications.
   @throws MediatorException if there is an error while mediating the notification.
   @throws ClientException if there is an error with the client.
   */
  void notify(TPayload payload) throws AggregatorException, MediatorException, ClientException;
}

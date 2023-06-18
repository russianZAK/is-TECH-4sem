package is.technologies.notifications;

import is.technologies.entities.*;
import is.technologies.exceptions.*;

import java.util.ArrayList;
import java.util.List;

/**
 The ChangePercentForDepositAccountAggregator class implements the IObservable interface
 and is responsible for aggregating and forwarding notifications related to changes in percents
 for deposit accounts to its subscribed observers.
 */
public class ChangePercentForDepositAccountAggregator implements Observable<Notification> {

  /**
   An ArrayList of observers that are interested in receiving notifications.
   */
  private final List<Observer<Notification>> observers;

  /**
   Constructs a new instance of ChangePercentForDepositAccountAggregator with an empty list of observers.
   */
  public ChangePercentForDepositAccountAggregator() {
    observers = new ArrayList<>();
  }

  /**
   Notifies all subscribed observers with the provided notification payload.
   @param payload The notification payload to be sent to all subscribed observers.
   @throws AggregatorException if the provided payload is null.
   @throws MediatorException if there is an issue with the mediator.
   @throws ClientException if there is an issue with the client.
   */
  public void notify(Notification payload) throws AggregatorException, MediatorException, ClientException {
    if (payload == null)
      throw AggregatorException.invalidPayload();

    observers.forEach(observer -> {
      try {
        observer.update(payload);
      } catch (MediatorException | ClientException e) {
        throw new RuntimeException(e);
      }
    });
  }

  /**
   Subscribes a new observer to receive notifications.
   @param observer The observer to subscribe.
   @throws AggregatorException if the provided observer is null.
   */
  public void subscribe(Observer<Notification> observer) throws AggregatorException {
    if (observer == null)
      throw AggregatorException.invalidObserver();
    observers.add(observer);
  }

  /**
   Unsubscribes an observer from receiving notifications.
   @param observer The observer to unsubscribe.
   @throws AggregatorException if the provided observer is null.
   */
  public void unsubscribe(Observer<Notification> observer) throws AggregatorException {
    if (observer == null)
      throw AggregatorException.invalidObserver();
    observers.remove(observer);
  }
}

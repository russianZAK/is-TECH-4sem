package is.technologies.entities;

import java.math.BigDecimal;
import is.technologies.models.*;
import is.technologies.notifications.*;
import is.technologies.exceptions.*;
import java.util.Objects;
import lombok.Getter;

/**
 The Mediator class is responsible for handling notifications and deciding whether they are useful or spam for a given client.
 */
@Getter
public class ClientMediator implements Mediator {

  /**
   Represents a client field.
   */
  private final Client client;

  /**
   Constructs a new mediator with the specified client.
   @param client The client to set for the mediator.
   @throws MediatorException if there is an error with the mediator or the client is null.
   */
  public ClientMediator(Client client) throws MediatorException {
    if (client == null)
      throw MediatorException.invalidClient();

    this.client = client;
  }

  /**
   This method evaluates notifications and determines whether they are useful or spam.
   It takes a Client object and an INotification object as input and then updates the notifications list for the client object.
   @param client The client object for which notifications will be evaluated.
   @param notification The notification object to be evaluated.
   @throws MediatorException If the client or notification objects are null.
   @throws ClientException If the notification cannot be added to the client's notifications list.
   */
  public void notify(Client client, Notification notification)
      throws MediatorException, ClientException {
    if (client == null)
      throw MediatorException.invalidClient();
    if (notification == null)
      throw MediatorException.invalidNotification();

    if (notification instanceof ChangeCommissionForCreditAccountNotification) {
      String[] message = notification.getMessage().split(" ");
      BigDecimal newCommision = new BigDecimal(message[0]);

      if (client.getBank().getCommissionForCreditAccount().compareTo(newCommision) > 0) {
        client.addUsefulNotification(notification);
      }
      else {
        client.addSpamNotification(notification);
      }
    }
    else if (notification instanceof ChangeCreditLimitNotification) {
      String[] message = notification.getMessage().split(" ");
      BigDecimal newCreditLimit = new BigDecimal(message[0]);

      if (client.getBank().getCreditLimit().compareTo(newCreditLimit) > 0) {
        client.addSpamNotification(notification);
      }
      else {
        client.addUsefulNotification(notification);
      }
    }
        else if (notification instanceof ChangePercentsForDebitAccountNotification) {
      String[] message = notification.getMessage().split(" ");
      BigDecimal newPercent = new BigDecimal(message[0]);

      if (client.getBank().getPercentForDebitAccounts().compareTo(newPercent) > 0) {
        client.addSpamNotification(notification);
      }
      else {
        client.addUsefulNotification(notification);
      }
    }
        else if (notification instanceof ChangeRestrictionForNotVerifiedCustomersNotification) {
      String[] message = notification.getMessage().split(" ");
      BigDecimal newRestriction = new BigDecimal(message[0]);

      if (client.getBank().getRestrictionForNotVerifiedCustomers().compareTo(newRestriction) > 0) {
        client.addSpamNotification(notification);
      }
      else {
        client.addUsefulNotification(notification);
      }
    }
        else if (notification instanceof ChangePercentForDepositAccountNotification) {
      String[] message = notification.getMessage().split(" ");
      String data = message[0];
      BigDecimal newPercent = new BigDecimal(message[1]);

      if (Objects.equals(data, "PercentLessFiftyThousand") && client.getBank().getPercentLessFiftyThousand().compareTo(newPercent) > 0) {
        client.addUsefulNotification(notification);
      }
      else {
        client.addSpamNotification(notification);
      }

      if (Objects.equals(data, "PercentLessOneHundredThousand") && client.getBank().getPercentLessOneHundredThousand().compareTo(newPercent) > 0) {
        client.addUsefulNotification(notification);
      }
      else {
        client.addSpamNotification(notification);
      }

      if (Objects.equals(data, "PercentMoreOneHundredThousand") && client.getBank().getPercentMoreOneHundredThousand().compareTo(newPercent) > 0) {
        client.addUsefulNotification(notification);
      }
      else {
        client.addSpamNotification(notification);
      }
    }
  }
}

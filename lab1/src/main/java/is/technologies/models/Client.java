package is.technologies.models;

import is.technologies.entities.*;
import is.technologies.exceptions.*;
import is.technologies.notifications.Notification;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;


/**
 The Client class represents a client of a bank, with basic personal information,
 bank accounts, and notifications.
 */
@Getter
public class Client implements Observer<Notification> {

  /**An ArrayList of IBankAccount objects that belong to this Client.*/
  private final List<BankAccount> bankAccounts;

  /**An ArrayList of INotification objects that are considered spam and not useful to the Client.*/
  private final List<Notification> spamNotifications;

  /**An ArrayList of INotification objects that are considered useful and relevant to the Client.*/
  private final List<Notification> usefulNotifications;

  /**The first name of the Client.*/
  private final String name;

  /**The surname of the Client.*/
  private final String surname;

  /**The address of the Client.*/
  private final String address;

  /**The passport number of the Client.*/
  private final String passport;

  /**A boolean value indicating whether the Client has been verified by the Bank.*/
  private final boolean isVerified;

  /**The Bank to which the Client is associated.*/
  private Bank bank;

  /**The mediator object used to coordinate communication between the Client and the Bank.*/
  private Mediator mediator;

  /**
   Constructs a new client with the specified builder parameters.
   @param builder the builder object containing client details.
   */
  public Client(Builder builder) {
    name = builder.firstName;
    surname = builder.surname;
    address = builder.address;
    passport = builder.passport;
    isVerified = builder.isVerified;
    bankAccounts = new ArrayList<>();
    spamNotifications = new ArrayList<>();
    usefulNotifications = new ArrayList<>();
  }

  /**
   Sets the mediator associated with the client.
   @param mediator the mediator to be associated with the client.
   @throws ClientException if the mediator is null.
   */
  public void setMediator(Mediator mediator) throws ClientException {
    if (mediator == null)
      throw ClientException.invalidMediator();
    this.mediator = mediator;
  }

  /**
   Adds a new bank account to the client's list of bank accounts.
   @param bankAccount the bank account to be added.
   @throws ClientException if the bank account is null.
   */
  public void setBankAccount(BankAccount bankAccount) throws ClientException {
    if (bankAccount == null)
      throw ClientException.invalidBankAccount();
    bankAccounts.add(bankAccount);
  }

  /**
   Sets the bank associated with the client.
   @param bank the bank to be associated with the client.
   @throws ClientException if the bank is null.
   */
  public void setBank(Bank bank) throws ClientException {
    if (bank == null)
      throw ClientException.invalidBank();
    this.bank = bank;
  }

  /**
   The update method is called by a subject, which this observer has been subscribed to,
   in order to pass an INotification payload to the observer.
   The method then passes the payload to the mediator to notify all other subscribed observers.
   @param payload The INotification payload passed to the observer.
   @throws MediatorException If an error occurs while notifying the mediator of the payload.
   @throws ClientException If the payload is invalid.
   */
  public void update(Notification payload) throws MediatorException, ClientException {
    if (payload == null)
      throw ClientException.invalidPayload();

    mediator.notify(this, payload);
  }

  /**
   Receives notifications from the mediator.
   @param payload the notification received from the mediator.
   @throws MediatorException if there is an error with the mediator.
   @throws ClientException if the payload is null.
   */
  public void addSpamNotification(Notification payload) throws ClientException {
    if (payload == null)
      throw ClientException.invalidPayload();

    spamNotifications.add(payload);
  }

  /**
   Adds a useful notification to the client's list of useful notifications.
   @param payload the notification to add
   @throws ClientException if the payload is null
   */
  public void addUsefulNotification(Notification payload) throws ClientException {
    if (payload == null)
      throw ClientException.invalidPayload();

    usefulNotifications.add(payload);
  }

  /**
   The Builder class is a static nested class within the Client class that provides a convenient way to create
   instances of Client class with complex parameters. This class allows to specify a Client object's attributes one by one,
   and then create an immutable Client object with the specified attributes using the build() method.
   */
  @Getter
  public static class Builder
  {
    /**The first name of the client.*/
    private String firstName;

    /**The surname of the client.*/
    private String surname;

    /**The address of the client.*/
    private String address;

    /**The passport number of the client.*/
    private String passport;

    /**A boolean indicating whether the client is verified or not.*/
    private boolean isVerified;

    /**
     Creates a new instance of the {@code Builder} class with empty field values.
     */
    public Builder() {
      firstName = "";
      surname = "";
      address = "";
      passport = "";
    }

    /**
     Sets the first name of the client in the builder.
     @param firstName the first name of the client
     @return the builder with the updated first name
     @throws ClientException if the provided first name is null
     */
    public Builder setFirstName(String firstName) throws ClientException {
      if (firstName == null)
        throw ClientException.invalidData();
      this.firstName = firstName;
      return this;
    }

    /**
     Sets the surname of the client in the builder.
     @param surname the second name of the client
     @return the builder with the updated surname
     @throws ClientException if the provided surname is null
     */
    public Builder setSurname(String surname) throws ClientException {
      if (surname == null)
        throw ClientException.invalidData();

      this.surname = surname;
      return this;
    }

    /**
     Sets the address of the client in the builder.
     @param address the address of the client
     @return the builder with the updated address
     @throws ClientException if the provided address is null
     */
    public Builder setAddress(String address) throws ClientException {
      if (address == null)
        throw ClientException.invalidData();

      this.address = address;
      return this;
    }

    /**
     Sets the passport of the client in the builder.
     @param passport the passport of the client
     @return the builder with the updated passport
     @throws ClientException if the provided passport is null
     */
    public Builder setPassport(String passport) throws ClientException {
      if (passport == null)
        throw ClientException.invalidData();

      this.passport = passport;
      return this;
    }

    /**
     Builds a new instance of {@link Client} based on the data provided to the builder.
     @return a new instance of {@link Client}
     */
    public Client build() {
      if (!Objects.equals(address, "") && !Objects.equals(passport, "")) {
        isVerified = true;
      }
      return new Client(this);
    }
  }
}

package is.technologies.models;
import is.technologies.entities.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import is.technologies.notifications.*;
import is.technologies.exceptions.*;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import lombok.Getter;


/**
 Bank represents a financial institution and has clients, bank accounts, and transactions
 It implements the IWatcher interface and observes ChangePercentsForDebitAccountAggregator, ChangeCommissionForCreditAccountAggregator,
 ChangeCreditLimitAggregator, ChangeRestrictionForNotVerifiedCustomersAggregator, and ChangePercentForDepositAccountAggregator classes.
 These classes allow Bank to make changes to the system.
 A Bank can add new clients and new bank accounts to a client.
 */
@Getter
public class Bank implements Watcher {

  /**The list of clients in the bank.*/
  private final List<Client> clients;

  /**The list of bank accounts in the bank.*/
  private final List<BankAccount> bankAccounts;

  /**The list of transactions in the bank.*/
  private final List<Transaction> transactions;

  /**The aggregator responsible for changing percents for debit accounts.*/
  private final ChangePercentsForDebitAccountAggregator changePercentsForDebitAccountAggregator;

  /**The aggregator responsible for changing commission for credit accounts.*/
  private final ChangeCommissionForCreditAccountAggregator changeCommissionForCreditAccountAggregator;

  /**The aggregator responsible for changing credit limits.*/
  private final ChangeCreditLimitAggregator changeCreditLimitAggregator;

  /**The aggregator responsible for changing restrictions for not verified customers.*/
  private final ChangeRestrictionForNotVerifiedCustomersAggregator changeRestrictionForNotVerifiedCustomersAggregator;

  /**The aggregator responsible for changing percent for deposit accounts.*/
  private final ChangePercentForDepositAccountAggregator changePercentForDepositAccountAggregator;

  /**The name of the bank.*/
  private final String name;

  /**The ID of the bank.*/
  private final int id;

  /**The percent for debit accounts.*/
  private BigDecimal percentForDebitAccounts;

  /**The percent for balances less than fifty thousand.*/
  private BigDecimal percentLessFiftyThousand;

  /**The percent for balances between fifty thousand and one hundred thousand.*/
  private BigDecimal percentLessOneHundredThousand;

  /**The percent for balances greater than one hundred thousand.*/
  private BigDecimal percentMoreOneHundredThousand;

  /**The commission for credit accounts.*/
  private BigDecimal commissionForCreditAccount;

  /**The restriction for not verified customers.*/
  private BigDecimal restrictionForNotVerifiedCustomers;

  /**The credit limit for credit accounts.*/
  private BigDecimal creditLimit;

  /**
   Creates a new Bank object with the given parameters and initializes the necessary fields.
   @param name the name of the bank, cannot be null
   @param id the identification number of the bank, must be a positive integer
   @param percentForDebitAccounts the interest rate for debit accounts, must be a non-negative decimal
   @param percentLessFiftyThousand the interest rate for debit accounts with balance less than $50,000, must be a non-negative decimal
   @param percentLessOneHundredThousand the interest rate for debit accounts with balance less than $100,000, must be a non-negative decimal
   @param percentMoreOneHundredThousand the interest rate for debit accounts with balance more than $100,000, must be a non-negative decimal
   @param commissionForCreditAccount the commission rate for credit accounts, must be a non-negative decimal
   @param restrictionForNotVerifiedCustomers the maximum amount a not-verified customer can withdraw, must be a non-negative decimal
   @param creditLimit the maximum credit limit for a customer, must be a non-negative decimal
   @throws BankException if any of the input parameters are invalid
   */
  public Bank(
      String name,
      int id,
      BigDecimal
          percentForDebitAccounts,
      BigDecimal percentLessFiftyThousand,
      BigDecimal percentLessOneHundredThousand,
      BigDecimal percentMoreOneHundredThousand,
      BigDecimal commissionForCreditAccount,
      BigDecimal restrictionForNotVerifiedCustomers,
      BigDecimal creditLimit) throws BankException {
    clients = new ArrayList<>();
    transactions = new ArrayList<>();
    bankAccounts = new ArrayList<>();
    changePercentsForDebitAccountAggregator = new ChangePercentsForDebitAccountAggregator();
    changeCommissionForCreditAccountAggregator = new ChangeCommissionForCreditAccountAggregator();
    changeCreditLimitAggregator = new ChangeCreditLimitAggregator();
    changeRestrictionForNotVerifiedCustomersAggregator = new ChangeRestrictionForNotVerifiedCustomersAggregator();
    changePercentForDepositAccountAggregator = new ChangePercentForDepositAccountAggregator();

    if (name == null)
      throw BankException.invalidName();
    if (id < 0)
      throw BankException.invalidId();
    if (percentForDebitAccounts.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentForDebitAccounts(percentForDebitAccounts);
    if (percentLessFiftyThousand.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentLessFiftyThousand(percentLessFiftyThousand);
    if (percentLessOneHundredThousand.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentLessOneHundredThousand(percentLessOneHundredThousand);
    if (percentMoreOneHundredThousand.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentMoreOneHundredThousand(percentMoreOneHundredThousand);
    if (commissionForCreditAccount.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidCommissionForCreditAccount(commissionForCreditAccount);
    if (restrictionForNotVerifiedCustomers.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidRestrictionForNotVerifiedCustomers(restrictionForNotVerifiedCustomers);
    if (creditLimit.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidCreditLimit(creditLimit);

    this.name = name;
    this.id = id;
    this.percentForDebitAccounts = percentForDebitAccounts;
    this.percentLessFiftyThousand = percentLessFiftyThousand;
    this.percentLessOneHundredThousand = percentLessOneHundredThousand;
    this.percentMoreOneHundredThousand = percentMoreOneHundredThousand;
    this.commissionForCreditAccount = commissionForCreditAccount;
    this.restrictionForNotVerifiedCustomers = restrictionForNotVerifiedCustomers;
    this.creditLimit = creditLimit;
  }

  /**
   Adds a new client to the bank.
   @param client the client to be added to the bank
   @throws BankException if the client parameter is null or there is an issue adding the client to the bank
   @throws AggregatorException if there is an issue subscribing the client to the aggregator
   @throws ClientException if there is an issue adding the bank to the client or the client is invalid
   */
  public void addNewClient(Client client) throws BankException, AggregatorException, ClientException {
    if (client == null)
      throw BankException.invalidClient();

    if (!client.isVerified())
      changeRestrictionForNotVerifiedCustomersAggregator.subscribe(client);
    client.setBank(this);
    clients.add(client);
  }

  /**
   Adds a new debit account to the specified client and subscribes the client to the Change Percents For Debit Account
   aggregator.
   @param client the client to add the new debit account to
   @throws BankException if the specified client is null or doesn't exist in the system
   @throws AggregatorException if the bank fails to subscribe to the aggregator
   @throws ClientException if the specified client fails to add the new bank account
   @throws BankAccountIdException if the generated bank account ID is invalid
   @throws BankAccountException if there is an error creating the new debit account
   */
  public void addNewDebitAccountToClient(Client client)
      throws BankException, AggregatorException, ClientException, BankAccountIdException, BankAccountException {
    if (client == null)
      throw BankException.invalidClient();
    if (!clients.contains(client))
      throw BankException.clientDoesntExistInSystem();

    var newDebitAccount = new DebitAccount(new BankAccountId(id, UUID.randomUUID()), client,
        percentForDebitAccounts, this);
    changePercentsForDebitAccountAggregator.subscribe(client);
    client.setBankAccount(newDebitAccount);
    bankAccounts.add(newDebitAccount);
  }

  /**
   Adds a new credit account to the specified client and subscribes the client to the Change Percents For Credit Account
   aggregator.
   @param client the client to add the new credit account to
   @throws BankException if the client is null or doesn't exist in the system
   @throws BankAccountIdException if there is an issue with the bank account ID
   @throws BankAccountException if there is an issue with the bank account
   @throws AggregatorException if there is an issue with an aggregator
   @throws ClientException if there is an issue with the client
   */
  public void addNewCreditAccountToClient(Client client)
      throws BankException, BankAccountIdException, BankAccountException, AggregatorException, ClientException {
    if (client == null)
      throw BankException.invalidClient();
    if (!clients.contains(client))
      throw BankException.clientDoesntExistInSystem();

    var newCreditAccount = new CreditAccount(new BankAccountId(id, UUID.randomUUID()), client,
        creditLimit,
        commissionForCreditAccount, this);
    changeCommissionForCreditAccountAggregator.subscribe(client);
    changeCreditLimitAggregator.subscribe(client);
    client.setBankAccount(newCreditAccount);
    bankAccounts.add(newCreditAccount);
  }

  /**
   Adds a new deposit account to the specified client with the given initial amount of money and opening time and subscribes the client to the Change Percents For Deposit Account
   aggregator.
   @param client the client to add the deposit account to
   @param money the initial amount of money to deposit into the account
   @param time the time when the account was opened
   @throws BankException if the bank has encountered an error
   @throws BankAccountIdException if there is an issue with the bank account ID
   @throws BankAccountException if there is an issue with the bank account
   @throws AggregatorException if there is an issue with the aggregator
   @throws ClientException if there is an issue with the client
   */
  public void addNewDepositAccountToClient(Client client, BigDecimal money, Calendar time)
      throws BankException, BankAccountIdException, BankAccountException, AggregatorException, ClientException {
    if (client == null)
      throw BankException.invalidClient();
    if (!clients.contains(client))
      throw BankException.clientDoesntExistInSystem();
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidAmountOfMoney(money);

    BigDecimal percent = BigDecimal.ZERO;

    if (money.compareTo(new BigDecimal(50000)) < 0)
      percent = percentLessFiftyThousand;
    if (money.compareTo(new BigDecimal(50000)) >= 0 && money.compareTo(new BigDecimal(100000)) < 0)
      percent = percentLessOneHundredThousand;
    if (money.compareTo(new BigDecimal(100000)) >= 0)
      percent = percentMoreOneHundredThousand;

    var newDepositAccount = new DepositAccount(new BankAccountId(id, UUID.randomUUID()), client, percent, time, money, this);
    changePercentForDepositAccountAggregator.subscribe(client);
    client.setBankAccount(newDepositAccount);
    bankAccounts.add(newDepositAccount);
  }

/**
 Executes a money top-up transaction on the given bank account with the specified amount of money.
 @param bankAccount the bank account to perform the transaction on
 @param money the amount of money to top-up
 @throws BankException if the specified amount of money is negative, the bank account is null, or the bank account does not exist in the system
 @throws TransactionException if an error occurs while executing the transaction
 @throws BankAccountException if an error occurs with the bank account during the transaction
 */
  public void moneyTopUpTransaction(BankAccount bankAccount, BigDecimal money)
      throws BankException, TransactionException, BankAccountException {
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidAmountOfMoney(money);
    if (bankAccount == null)
      throw BankException.invalidBankAccount();
    if (!bankAccounts.contains(bankAccount))
      throw BankException.bankAccountDoesntExistInSystem();

    var newMoneyTopUpTransaction = new MoneyTopUpTransaction(bankAccount, money);
    newMoneyTopUpTransaction.execute();
    transactions.add(newMoneyTopUpTransaction);
  }

  /**
   Executes a money withdrawal transaction on the specified bank account with the given amount of money.
   @param bankAccount the bank account from which the money will be withdrawn
   @param money the amount of money to withdraw
   @throws BankException if the bank account or the amount of money is invalid, or if the operation is not allowed for the owner of the bank account
   @throws TransactionException if the transaction cannot be executed
   @throws BankAccountException if there is an issue with the bank account
   */
  public void moneyWithdrawTransaction(BankAccount bankAccount, BigDecimal money)
      throws BankException, TransactionException, BankAccountException {
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidAmountOfMoney(money);
    if (bankAccount == null)
      throw BankException.invalidBankAccount();
    if (!bankAccounts.contains(bankAccount))
      throw BankException.bankAccountDoesntExistInSystem();

    if (!bankAccount.getOwner().isVerified() && money.compareTo(restrictionForNotVerifiedCustomers) > 0)
      throw BankException.invalidOperation();

    var newMoneyWithdrawTransaction = new MoneyWithdrawTransaction(bankAccount, money);
    newMoneyWithdrawTransaction.execute();
    newMoneyWithdrawTransaction.execute();
    transactions.add(newMoneyWithdrawTransaction);
  }

  /**
   Adds a money transfer transaction to the bank's list of transactions.
   @param transaction The transaction to add to the list.
   @throws BankException If the transaction is null.
   */
  public void addMoneyTransferTransaction(Transaction transaction) throws BankException {
    if (transaction == null)
      throw BankException.invalidTransaction();
    transactions.add(transaction);
  }

  /**
   Rolls back a previously executed transaction.
   @param transaction The transaction to rollback.
   @return The rolled back transaction.
   @throws BankException If the transaction is null or doesn't exist in the system.
   @throws TransactionException If an error occurs while rolling back the transaction.
   @throws BankAccountException If an error occurs with a bank account involved in the transaction.
   */
  public Transaction rollback(Transaction transaction)
      throws BankException, TransactionException, BankAccountException {
    if (transaction == null)
      throw BankException.invalidTransaction();
    if (!transactions.contains(transaction))
      throw BankException.transactionDoesntExistInSystem();

    transaction.rollback();

    return transaction;
  }

  /**
   Creates and executes a new MoneyTransferTransaction between two bank accounts.
   @param bankAccountFrom the bank account from which the money will be transferred
   @param bankAccountTo the bank account to which the money will be transferred
   @param money the amount of money to be transferred
   @return the created MoneyTransferTransaction
   @throws BankException if there is an error related to the bank
   @throws TransactionException if there is an error related to the transaction
   @throws BankAccountException if there is an error related to the bank account
   */
  public MoneyTransferTransaction moneyTransferTransaction(
      BankAccount bankAccountFrom, BankAccount bankAccountTo, BigDecimal money)
      throws BankException, TransactionException, BankAccountException {
    if (bankAccountFrom == null)
      throw BankException.invalidBankAccount();
    if (bankAccountTo == null)
      throw BankException.invalidBankAccount();
    if (!bankAccounts.contains(bankAccountFrom))
      throw BankException.bankAccountDoesntExistInSystem();
    if (!bankAccounts.contains(bankAccountTo))
      throw BankException.bankAccountDoesntExistInSystem();
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidAmountOfMoney(money);

    if (!bankAccountFrom.getOwner().isVerified() && money.compareTo(
        restrictionForNotVerifiedCustomers) > 0) throw BankException.invalidOperation();

    var newMoneyTransferTransaction = new MoneyTransferTransaction(bankAccountFrom, bankAccountTo, money);
    newMoneyTransferTransaction.execute();
    transactions.add(newMoneyTransferTransaction);
    return newMoneyTransferTransaction;
  }

  /**
   Changes the percent for new debit accounts to the specified value.
   @param newPercent the new percent for new debit accounts
   @throws BankException if the new percent is negative
   @throws AggregatorException if an error occurs in the aggregator
   @throws NotificationException if an error occurs while sending notifications
   @throws MediatorException if an error occurs while sending notifications to mediator
   @throws ClientException if an error occurs with the client
   */
  public void changePercentForDebitAccounts(BigDecimal newPercent)
      throws BankException, AggregatorException, NotificationException, MediatorException, ClientException {
    if (newPercent.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentForDebitAccounts(newPercent);

    percentForDebitAccounts = newPercent;
    var newNotification = new ChangePercentsForDebitAccountNotification(newPercent + " - new percent for new debit accounts");
    changePercentsForDebitAccountAggregator.notify(newNotification);
  }

  /**
   Changes the commission for credit accounts to the given new commission.
   Notifies all subscribed clients about the change through the appropriate aggregator.
   @param newCommission the new commission to be set for credit accounts.
   @throws BankException if the new commission is negative.
   @throws NotificationException if there is an error in notifying subscribed clients.
   @throws AggregatorException if there is an error with the aggregator.
   @throws MediatorException if there is an error with the mediator.
   @throws ClientException if there is an error with the client.
   */
  public void changeCommissionForCreditAccount(BigDecimal newCommission)
      throws BankException, NotificationException, AggregatorException, MediatorException, ClientException {
    if (newCommission.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidCommissionForCreditAccount(newCommission);

    commissionForCreditAccount = newCommission;
    var newNotification = new ChangeCommissionForCreditAccountNotification(newCommission + " - new commission for credit accounts");
    changeCommissionForCreditAccountAggregator.notify(newNotification);
  }

  /**
   Changes the credit limit for credit accounts and notifies all registered observers/aggregators.
   @param newCreditLimit the new credit limit to set
   @throws BankException if the new credit limit is negative
   @throws NotificationException if there is an error while notifying the observers/aggregators
   @throws AggregatorException if there is an error while aggregating the notifications
   @throws MediatorException if there is an error while mediating between the bank and the clients
   @throws ClientException if there is an error with the clients
   */
  public void changeCreditLimit(BigDecimal newCreditLimit)
      throws BankException, NotificationException, AggregatorException, MediatorException, ClientException {
    if (newCreditLimit.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidCreditLimit(newCreditLimit);

    creditLimit = newCreditLimit;
    var newNotification = new ChangeCreditLimitNotification(newCreditLimit + " - new creditlimit for credit accounts");
    changeCreditLimitAggregator.notify(newNotification);
  }

  /**
   Changes the restriction for not verified customers to a new value and notifies all subscribed aggregators, mediators,
   and clients about the change using the {@link ChangeRestrictionForNotVerifiedCustomersNotification} notification.
   @param newRestriction the new restriction for not verified customers
   @throws BankException if the new restriction is invalid (negative)
   @throws NotificationException if there is an issue with sending notifications to subscribed entities
   @throws AggregatorException if there is an issue with aggregating notifications from subscribed entities
   @throws MediatorException if there is an issue with mediating between different entities
   @throws ClientException if there is an issue with handling client requests or data
   */
  public void changeRestrictionForNotVerifiedCustomersAggregator(BigDecimal newRestriction)
      throws BankException, NotificationException, AggregatorException, MediatorException, ClientException {
    if (newRestriction.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidRestrictionForNotVerifiedCustomers(newRestriction);

    restrictionForNotVerifiedCustomers = newRestriction;
    var newNotification = new ChangeRestrictionForNotVerifiedCustomersNotification(newRestriction + " - new restriction for not verified clients");
    changeRestrictionForNotVerifiedCustomersAggregator.notify(newNotification);
  }

  /**
   Changes the percent for deposit accounts with balance less than 50,000.
   @param newPercent the new percent for deposit accounts with balance less than 50,000.
   @throws BankException if the new percent is negative.
   @throws NotificationException if there was an issue sending a notification.
   @throws AggregatorException if there was an issue with the notification aggregator.
   @throws MediatorException if there was an issue with the notification mediator.
   @throws ClientException if there was an issue with the notification client.
   */
  public void changePercentLessFiftyThousand(BigDecimal newPercent)
      throws BankException, NotificationException, AggregatorException, MediatorException, ClientException {
    if (newPercent.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentLessFiftyThousand(newPercent);

    percentLessFiftyThousand = newPercent;
    var newNotification = new ChangePercentForDepositAccountNotification("PercentLessFiftyThousand" + newPercent + " !");
    changePercentForDepositAccountAggregator.notify(newNotification);
  }

  /**
   Changes the percent rate for deposit accounts with balances less than 100,000.
   @param newPercent the new percent rate for deposit accounts less than 100,000
   @throws BankException if the new percent rate is negative
   @throws NotificationException if there is an error sending the notification
   @throws AggregatorException if there is an error notifying the aggregator
   @throws MediatorException if there is an error communicating with the mediator
   @throws ClientException if there is an error retrieving client information
   */
  public void changePercentLessOneHundredThousand(BigDecimal newPercent)
      throws BankException, NotificationException, AggregatorException, MediatorException, ClientException {
    if (newPercent.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentLessOneHundredThousand(newPercent);

    percentLessOneHundredThousand = newPercent;
    var newNotification = new ChangePercentForDepositAccountNotification("PercentLessOneHundredThousand" + newPercent + " !");
    changePercentForDepositAccountAggregator.notify(newNotification);
  }

  /**
   Changes the interest percent for deposit accounts with balance more than 100,000.
   Throws BankException if the new percent is negative.
   Notifies the change to the changePercentForDepositAccountAggregator.
   @param newPercent the new interest percent to be set for deposit accounts with balance more than 100,000.
   @throws BankException if the new percent is negative.
   @throws NotificationException if there is an error with the notification process.
   @throws AggregatorException if there is an error with the aggregator process.
   @throws MediatorException if there is an error with the mediator process.
   @throws ClientException if there is an error with the client process.
   */
  public void changePercentMoreOneHundredThousand(BigDecimal newPercent)
      throws BankException, NotificationException, AggregatorException, MediatorException, ClientException {
    if (newPercent.compareTo(BigDecimal.ZERO) < 0)
      throw BankException.invalidPercentMoreOneHundredThousand(newPercent);

    percentMoreOneHundredThousand = newPercent;
    var newNotification = new ChangePercentForDepositAccountNotification("PercentMoreOneHundredThousand" + newPercent + " !");
    changePercentForDepositAccountAggregator.notify(newNotification);
  }

  /**
   Removes the given transaction from the list of transactions.
   @param transaction the transaction to remove
   @throws TransactionException if the given transaction is null
   */
  public void deleteTransaction(Transaction transaction) throws TransactionException {
    if (transaction == null)
      throw TransactionException.invalidTransaction();

    transactions.remove(transaction);
  }

  /**
   Updates the date of the bank system and calls the dayChange method of all bank accounts.
   If the given calendar's day is the last day of the month, isLastDayOfMonth is set to true.
   @param time the calendar representing the new date to set
   */
  public void updateDate(Calendar time)
  {
    int lastDayOfMonth = time.getActualMaximum(Calendar.DAY_OF_MONTH);

    boolean isLastDayOfMonth;

    isLastDayOfMonth = lastDayOfMonth == time.get(Calendar.DAY_OF_MONTH);

    bankAccounts.forEach(bankAccount ->bankAccount.dayChange(isLastDayOfMonth));

  }
}

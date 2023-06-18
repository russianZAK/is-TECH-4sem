package is.technologies.service;

import is.technologies.entities.Subject;
import is.technologies.models.*;
import is.technologies.entities.*;
import is.technologies.exceptions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import lombok.Getter;

/**
 The CentralBank class represents a central bank that manages multiple banks and transactions between them.
 It implements the ISubject interface, which defines methods for attaching and detaching watchers to the central bank.
 */
@Getter
public class CentralBank implements Subject {

  /**The list of all the banks in the system.*/
  private final ArrayList<Bank> banks;

  /**The list of all the money transfer transactions in the system.*/
  private final ArrayList<MoneyTransferTransaction> transactions;

  /**The list of all the watchers in the system.*/
  private final ArrayList<Watcher> watchers;

  /**The unique ID of the system.*/
  private int id;

  /**The time provider used to get the current time.*/
  private final TimeProvider timeProvider;

  /**
   Constructs a new CentralBank object with a specified TimeProvider.
   @param timeProvider the TimeProvider to be used by the CentralBank for time-related operations
   */
  public CentralBank(TimeProvider timeProvider)
  {
    id = 0;
    banks = new ArrayList<>();
    transactions = new ArrayList<>();
    this.timeProvider = timeProvider;
    watchers = new ArrayList<>();
  }

  /**
   Adds a new bank to the list of banks managed by the central bank.
   @param name the name of the bank to add.
   @param percentForDebitAccounts the interest rate for debit accounts of the new bank.
   @param percentLessFiftyThousand the interest rate for accounts with balance less than 50,000 of the new bank.
   @param percentLessOneHundredThousand the interest rate for accounts with balance less than 100,000 of the new bank.
   @param percentMoreOneHundredThousand the interest rate for accounts with balance more than or equal to 100,000 of the new bank.
   @param commissionForCreditAccount the commission rate for credit accounts of the new bank.
   @param restrictionForNotVerifiedCustomers the maximum amount of money that can be transferred by a non-verified customer of the new bank.
   @param creditLimit the maximum credit limit for a credit account of the new bank.
   @return a Bank object representing the newly added bank.
   @throws BankException if any of the input parameters are invalid.
   */
  public Bank addNewBank(String name, BigDecimal percentForDebitAccounts, BigDecimal percentLessFiftyThousand, BigDecimal percentLessOneHundredThousand, BigDecimal percentMoreOneHundredThousand, BigDecimal commissionForCreditAccount, BigDecimal restrictionForNotVerifiedCustomers, BigDecimal creditLimit)
      throws BankException {
    if (name == null) throw BankException.invalidName();
    if (percentForDebitAccounts.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidPercentForDebitAccounts(percentForDebitAccounts);
    if (percentLessFiftyThousand.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidPercentLessFiftyThousand(percentLessFiftyThousand);
    if (percentLessOneHundredThousand.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidPercentLessOneHundredThousand(percentLessOneHundredThousand);
    if (percentMoreOneHundredThousand.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidPercentMoreOneHundredThousand(percentMoreOneHundredThousand);
    if (commissionForCreditAccount.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidCommissionForCreditAccount(commissionForCreditAccount);
    if (restrictionForNotVerifiedCustomers.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidRestrictionForNotVerifiedCustomers(restrictionForNotVerifiedCustomers);
    if (creditLimit.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidCreditLimit(creditLimit);

    var newBank = new Bank(name, id, percentForDebitAccounts, percentLessFiftyThousand, percentLessOneHundredThousand, percentMoreOneHundredThousand, commissionForCreditAccount, restrictionForNotVerifiedCustomers, creditLimit);
    banks.add(newBank);
    watchers.add(newBank);
    id++;
    return newBank;
  }

  /**
   Transfers a specified amount of money from one bank account to another.
   @param bankAccountFrom the bank account from which the money will be transferred
   @param bankAccountTo the bank account to which the money will be transferred
   @param money the amount of money to be transferred
   @return the new money transfer transaction created as a result of the transfer
   @throws BankException if there is an issue with the bank(s) involved in the transaction
   @throws TransactionException if there is an issue with the transaction itself
   @throws BankAccountException if there is an issue with one or both bank accounts involved in the transaction
   */
  public MoneyTransferTransaction moneyTransferTransaction(
      BankAccount bankAccountFrom, BankAccount bankAccountTo, BigDecimal money)
      throws BankException, TransactionException, BankAccountException {
    if (bankAccountFrom == null) throw BankException.invalidBankAccount();
    if (bankAccountTo == null) throw BankException.invalidBankAccount();
    if (money.compareTo(BigDecimal.ZERO) < 0) throw BankException.invalidAmountOfMoney(money);

    if (!bankAccountFrom.getOwner().isVerified() && money.compareTo(bankAccountFrom.getBank().getRestrictionForNotVerifiedCustomers()) > 0) throw BankException.invalidOperation();

    var newMoneyTransferTransaction = new MoneyTransferTransaction(bankAccountFrom, bankAccountTo, money);
    newMoneyTransferTransaction.execute();
    transactions.add(newMoneyTransferTransaction);

    if (bankAccountFrom.getBank() == bankAccountTo.getBank())
    {
      bankAccountFrom.getBank().addMoneyTransferTransaction(newMoneyTransferTransaction);
    }
    else
    {
      bankAccountFrom.getBank().addMoneyTransferTransaction(newMoneyTransferTransaction);
      bankAccountTo.getBank().addMoneyTransferTransaction(newMoneyTransferTransaction);
    }

    return newMoneyTransferTransaction;
  }

  /**
   Rolls back a given {@link MoneyTransferTransaction}.
   @param transaction the {@link MoneyTransferTransaction} to be rolled back.
   @return the rolled back {@link MoneyTransferTransaction}.
   @throws BankException if there is a problem with the bank system.
   @throws BankAccountException if there is a problem with one of the bank accounts.
   @throws TransactionException if there is a problem with the transaction.
   */
  public MoneyTransferTransaction rollback(MoneyTransferTransaction transaction)
      throws BankException, BankAccountException, TransactionException {
    if (transaction == null) throw BankException.invalidTransaction();
    if (!transactions.contains(transaction)) throw BankException.transactionDoesntExistInSystem();

    transaction.rollback();

    return transaction;
  }

  /**
   Attaches a new watcher to the Central Bank's list of watchers.
   @param watcher the watcher to be attached
   @throws CentralBankException if the watcher is null
   */
  public void attach(Watcher watcher) throws CentralBankException {
    if (watcher == null) throw CentralBankException.invalidWatcher();
    watchers.add(watcher);
  }

  /**
   Detaches a watcher from the Central Bank's list of watchers.
   @param watcher the watcher to be detached
   @throws CentralBankException if the watcher is null
   */
  public void detach(Watcher watcher) throws CentralBankException {
    if (watcher == null) throw CentralBankException.invalidWatcher();
    watchers.remove(watcher);
  }

  /**
   Notifies the Central Bank that a transaction has been rolled back and deletes the transaction from the
   corresponding banks' transaction lists.
   @param transaction the transaction that was rolled back
   @throws TransactionException if the transaction is null
   */
  public void rolledBackTransaction(Transaction transaction) throws TransactionException {
    if (transaction == null) throw TransactionException.invalidTransaction();

    if (transaction.getAccountFrom().getBank() == transaction.getAccountTo().getBank())
    {
      transaction.getAccountFrom().getBank().deleteTransaction(transaction);
    }
    else
    {
      transaction.getAccountFrom().getBank().deleteTransaction(transaction);
      transaction.getAccountTo().getBank().deleteTransaction(transaction);
    }
  }

  /**
   Changes the current date to the given time and notifies all the registered watchers of the change.
   @param time the new time to be set
   */
  public void changeDate(Calendar time)
  {
    Calendar newDate = timeProvider.getDate();

    while (timeProvider.getDate().compareTo(time) != 0)
    {
      watchers.forEach(bank ->
          bank.updateDate(timeProvider.getDate()));
      newDate.add(Calendar.DAY_OF_MONTH, 1);
      timeProvider.changeDate(newDate);
    }
  }

}

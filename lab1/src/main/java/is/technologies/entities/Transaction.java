package is.technologies.entities;

import is.technologies.exceptions.*;
import java.math.BigDecimal;

/**
 Represents a transaction interface.
 */
public interface Transaction {
  /**
   Gets the account the money is coming from.
   @return The account the money is coming from.
   */
  BankAccount getAccountFrom();

  /**
   Gets the account the money is going to.
   @return The account the money is going to.
   */
  BankAccount getAccountTo();

  /**
   Gets the amount of money being transferred.
   @return The amount of money being transferred.
   */
  BigDecimal getMoney();

  /**
   Checks if the money has been transferred.
   @return true if the money has been transferred, false otherwise.
   */
  boolean isMoneyTransferred();

  /**
   Rolls back the transaction.
   @throws TransactionException if there is an error with the transaction.
   @throws BankAccountException if there is an error with the bank account.
   */
  void rollback() throws TransactionException, BankAccountException;

  /**
   Executes the transaction.
   @throws TransactionException if there is an error with the transaction.
   @throws BankAccountException if there is an error with the bank account.
   */
  void execute() throws TransactionException, BankAccountException;
}

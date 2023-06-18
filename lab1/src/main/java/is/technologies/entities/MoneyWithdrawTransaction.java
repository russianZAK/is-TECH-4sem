package is.technologies.entities;

import java.math.BigDecimal;
import is.technologies.exceptions.*;
import lombok.Getter;

/**
 A class representing a money withdraw transaction from a bank account.
 */
@Getter
public class MoneyWithdrawTransaction implements Transaction {

  /**
   Represents the amount of money to be transferred in this transaction.
   */
  private final BigDecimal money;

  /**
   Represents a flag indicating whether the money transfer has been completed.
   This flag will be set to true after the transaction has been executed
   and the money has been transferred from accountFrom to accountTo.
   */
  private boolean isMoneyTransferred;

  /**
   Represents the bank account from which the money will be transferred.
   */
  private final BankAccount accountFrom;

  /**
   Represents the bank account to which the money will be transferred.
   */
  private final BankAccount accountTo;

  /**
   Represents a transaction for withdrawing money from a bank account.
   @param account the bank account from which the money will be withdrawn.
   @param money the amount of money to withdraw from the account.
   @throws TransactionException if the provided bank account or money amount is invalid.
   */
  public MoneyWithdrawTransaction(BankAccount account, BigDecimal money)
      throws TransactionException {
    if (account == null)
      throw TransactionException.invalidBankAccount();
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw TransactionException.invalidMoney(money);

    accountFrom = account;
    accountTo = account;
    this.money = money;
    isMoneyTransferred = false;
  }

  /**
   Executes the money withdraw transaction.
   @throws TransactionException if the transaction is invalid or has already been executed.
   @throws BankAccountException if the withdraw operation fails.
   */
  public void execute() throws TransactionException, BankAccountException {
    if (isMoneyTransferred)
      throw TransactionException.invalidTransaction();
    if (!accountFrom.isWithdrawAllowed(money))
      throw TransactionException.invalidTransaction();
    accountFrom.withdraw(money);
    isMoneyTransferred = true;
  }

  /**
   Rolls back the money withdraw transaction.
   @throws TransactionException if the transaction is invalid or has not been executed.
   @throws BankAccountException if the top-up operation fails.
   */
  public void rollback() throws TransactionException, BankAccountException {
    if (!isMoneyTransferred)
      throw TransactionException.invalidTransaction();
    accountFrom.topUp(money);
  }
}

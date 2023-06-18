package is.technologies.entities;

import java.math.BigDecimal;
import is.technologies.exceptions.*;
import lombok.Getter;

/**
 This class represents a money transfer transaction between two bank accounts.
 */
@Getter
public class MoneyTransferTransaction implements Transaction {

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
   Represents a transaction to transfer money from one bank account to another.
   @param bankAccountFrom The bank account from which the money will be transferred.
   @param bankAccountTo The bank account to which the money will be transferred.
   @param money The amount of money to be transferred.
   @throws TransactionException If either bankAccountFrom or bankAccountTo is null, or if the money value is negative.
   */
  public MoneyTransferTransaction(BankAccount bankAccountFrom, BankAccount bankAccountTo, BigDecimal money)
      throws TransactionException {
    if (bankAccountFrom == null)
      throw TransactionException.invalidBankAccount();
    if (bankAccountTo == null)
      throw TransactionException.invalidBankAccount();
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw TransactionException.invalidMoney(money);

    accountFrom = bankAccountFrom;
    accountTo = bankAccountTo;
    this.money = money;
    isMoneyTransferred = false;
  }

  /**
   Executes the money transfer transaction.
   @throws TransactionException if the transaction has already been executed or if the transfer is not allowed for any reason.
   @throws BankAccountException if there is an error with the bank accounts.
   */
  public void execute() throws TransactionException, BankAccountException {
    if (isMoneyTransferred) {
      throw TransactionException.invalidTransaction();
    }

    if (!accountFrom.isWithdrawAllowed(money))
      throw TransactionException.invalidTransaction();
    accountFrom.withdraw(money);
    accountTo.topUp(money);

    isMoneyTransferred = true;
  }

  /**
   Rolls back the money transfer transaction.
   @throws TransactionException if the transaction has not been executed or if there is an error rolling back the transaction.
   @throws BankAccountException if there is an error with the bank accounts.
   */
  public void rollback() throws TransactionException, BankAccountException {
    if (!isMoneyTransferred)
      throw TransactionException.invalidTransaction();
    accountTo.withdraw(money);
    accountFrom.topUp(money);
  }
}

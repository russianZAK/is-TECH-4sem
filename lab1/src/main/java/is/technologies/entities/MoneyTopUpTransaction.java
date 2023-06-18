package is.technologies.entities;

import is.technologies.exceptions.*;
import java.math.BigDecimal;
import lombok.Getter;


/**
 The MoneyTopUpTransaction class represents a transaction to top up money in a bank account.
 */
@Getter
public class MoneyTopUpTransaction implements Transaction {

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
   Constructs a new MoneyTopUpTransaction with the specified bank account and amount of money.
   @param account The bank account to use for the transaction.
   @param money The amount of money to transfer.
   @throws TransactionException if there is an error with the transaction, such as an invalid bank account or amount of money.
   */
  public MoneyTopUpTransaction(BankAccount account, BigDecimal money) throws TransactionException {
    if (account == null)
      throw TransactionException.invalidBankAccount();
    if (money.compareTo(BigDecimal.ZERO) < 0)
      throw TransactionException.invalidMoney(money);
    this.accountFrom = account;
    this.accountTo = account;
    this.money = money;
    this.isMoneyTransferred = false;
  }

  /**
   Executes the transaction by adding the specified amount of money to the bank account.
   @throws TransactionException if the transaction has already been executed or the bank account is invalid
   @throws BankAccountException if the bank account operation fails
   */
  public void execute() throws TransactionException, BankAccountException {
    if (isMoneyTransferred)
      throw TransactionException.invalidTransaction();
    accountFrom.topUp(money);
    isMoneyTransferred = true;
  }

  /**
   Rolls back the money top-up transaction.
   @throws TransactionException if the transaction is invalid or an error occurs during the transaction
   @throws BankAccountException if an error occurs while updating the bank account
   */
  public void rollback() throws TransactionException, BankAccountException {
    if (!accountFrom.isWithdrawAllowed(money))
      throw TransactionException.invalidTransaction();
    if (!isMoneyTransferred)
      throw TransactionException.invalidTransaction();
    accountFrom.withdraw(money);
  }
}

package is.technologies.exceptions;

import java.math.BigDecimal;

/**
 An exception class representing errors that may occur in the Transaction class.
 */
public class TransactionException extends Exception{
  private TransactionException(String message)
  {}

  public static TransactionException invalidBankAccount() {
    return new TransactionException("bank account is invalid");
  }

  public static TransactionException invalidMoney(BigDecimal money) {
    return new TransactionException(money + "is invalid");
  }

  public static TransactionException invalidTransaction() {
    return new TransactionException("transaction is invalid");
  }
}

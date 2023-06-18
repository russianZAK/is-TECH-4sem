package is.technologies.exceptions;

import java.math.BigDecimal;


/**
 An exception class representing errors that may occur in the Bank class.
 */
public class BankException extends Exception{
  private static final String INVALID_MESSAGE = " is invalid";
  private BankException(String message) {}

  public static BankException invalidName() {
    return new BankException("name is invalid");
  }

  public static BankException invalidId() {
    return new BankException("id is invalid");
  }

  public static BankException invalidPercentForDebitAccounts(BigDecimal percentForDebitAccounts) {
    return new BankException(percentForDebitAccounts + INVALID_MESSAGE);
  }

  public static BankException invalidPercentLessFiftyThousand(BigDecimal percentLessFiftyThousand) {
    return new BankException(percentLessFiftyThousand + INVALID_MESSAGE);
  }

  public static BankException invalidPercentLessOneHundredThousand(BigDecimal percentLessOneHundredThousand) {
    return new BankException(percentLessOneHundredThousand + INVALID_MESSAGE);
  }

  public static BankException invalidPercentMoreOneHundredThousand(BigDecimal percentMoreOneHundredThousand) {
    return new BankException(percentMoreOneHundredThousand + INVALID_MESSAGE);
  }

  public static BankException invalidCommissionForCreditAccount(BigDecimal commissionForCreditAccount) {
    return new BankException(commissionForCreditAccount + INVALID_MESSAGE);
  }

  public static BankException invalidRestrictionForNotVerifiedCustomers(BigDecimal restrictionForNotVerifiedCustomers) {
    return new BankException(restrictionForNotVerifiedCustomers + INVALID_MESSAGE);
  }

  public static BankException invalidCreditLimit(BigDecimal creditLimit) {
    return new BankException(creditLimit + INVALID_MESSAGE);
  }

  public static BankException invalidClient() {
    return new BankException("client is invalid");
  }

  public static BankException invalidBankAccount() {
    return new BankException("Bank account is invalid");
  }

  public static BankException invalidTransaction() {
    return new BankException("Transaction is invalid");
  }

  public static BankException clientDoesntExistInSystem() {
    return new BankException("client doesn't exist in system");
  }

  public static BankException bankAccountDoesntExistInSystem() {
    return new BankException("Bank account doesn't exist in system");
  }

  public static BankException transactionDoesntExistInSystem() {
    return new BankException("Transaction doesn't exist in system");
  }

  public static BankException invalidAmountOfMoney(BigDecimal money) {
    return new BankException(money + INVALID_MESSAGE);
  }

  public static BankException invalidOperation() {
    return new BankException("operation is invalid");
  }
}

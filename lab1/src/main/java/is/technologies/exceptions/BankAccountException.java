package is.technologies.exceptions;

import java.math.BigDecimal;


/**
 An exception class representing errors that may occur in the BankAccount class.
 */
public class BankAccountException extends Exception{
  private static final String INVALID_MESSAGE = " is invalid";
  private BankAccountException(String message) {}

  public static BankAccountException invalidPercentForDebitAccounts(
      BigDecimal percentForDebitAccounts) {
    return new BankAccountException(percentForDebitAccounts + INVALID_MESSAGE);
  }

  public static BankAccountException invalidPercentLessFiftyThousand(BigDecimal percentLessFiftyThousand) {
    return new BankAccountException(percentLessFiftyThousand + INVALID_MESSAGE);
  }

  public static BankAccountException invalidPercentLessOneHundredThousand(BigDecimal percentLessOneHundredThousand) {
    return new BankAccountException(percentLessOneHundredThousand + INVALID_MESSAGE);
  }

  public static BankAccountException invalidPercentMoreOneHundredThousand(BigDecimal percentMoreOneHundredThousand) {
    return new BankAccountException(percentMoreOneHundredThousand + INVALID_MESSAGE);
  }

  public static BankAccountException invalidCommissionForCreditAccount(BigDecimal commissionForCreditAccount) {
    return new BankAccountException(commissionForCreditAccount + INVALID_MESSAGE);
  }

  public static BankAccountException invalidRestrictionForNotVerifiedCustomers(BigDecimal restrictionForNotVerifiedCustomers) {
    return new BankAccountException(restrictionForNotVerifiedCustomers + INVALID_MESSAGE);
  }

  public static BankAccountException invalidCreditLimit(BigDecimal creditLimit) {
    return new BankAccountException(creditLimit + INVALID_MESSAGE);
  }

  public static BankAccountException invalidAmountOfMoney(BigDecimal money) {
    return new BankAccountException(money + INVALID_MESSAGE);
  }

  public static BankAccountException invalidClient() {
    return new BankAccountException("client is invalid");
  }

  public static BankAccountException invalidBankAccountId() {
    return new BankAccountException("bank account id is invalid");
  }

  public static BankAccountException invalidBank() {
    return new BankAccountException("bank id is invalid");
  }

  public static BankAccountException invalidPercent(BigDecimal percent) {
    return new BankAccountException(percent + "is invalid");
  }

  public static BankAccountException invalidBalance(BigDecimal balance) {
    return new BankAccountException(balance + "is invalid");
  }
}

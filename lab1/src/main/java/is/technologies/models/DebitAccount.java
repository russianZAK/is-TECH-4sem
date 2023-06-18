package is.technologies.models;

import is.technologies.entities.BankAccount;
import is.technologies.entities.*;
import is.technologies.exceptions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;


/**
 A class representing a debit bank account.
 Implements the IBankAccount interface.
 */
@Getter
public class DebitAccount implements BankAccount {

  /**The interest rate percent for this account.*/
  private final BigDecimal percent;

  /**The total amount of interest accruals for this account.*/
  private BigDecimal accruals;

  /**The current balance of this account.*/
  private BigDecimal balance;

  /**The bank associated with this account.*/
  private final Bank bank;

  /**The unique identifier for this account.*/
  private final BankAccountId bankAccountId;

  /**The client who owns this account.*/
  private final Client owner;

  /**
   Creates a new DebitAccount with the specified ID, owner, interest rate, and bank.
   @param accountId the ID of this bank account
   @param owner the owner of this bank account
   @param percent the interest rate for this account
   @param bank the bank associated with this account
   @throws BankAccountException if the percent is negative, the owner is null, the account ID is null, or the bank is null
   */
  public DebitAccount(BankAccountId accountId, Client owner, BigDecimal percent, Bank bank)
      throws BankAccountException {
    if (percent.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidPercentForDebitAccounts(percent);
    if (owner == null)
      throw BankAccountException.invalidClient();
    if (accountId == null)
      throw BankAccountException.invalidBankAccountId();
    if (bank == null)
      throw BankAccountException.invalidBank();

    balance = BigDecimal.ZERO;
    accruals = BigDecimal.ZERO;
    bankAccountId = accountId;
    this.owner = owner;
    this.percent = percent;
    this.bank = bank;
  }

  /**
   Returns the balance of this account.
   @return the balance of this account
   */
  public BigDecimal getLimit() {
    return balance;
  }

  /**
   Performs daily changes on the account.
   @param isLastDayOfMonth a boolean indicating whether or not it is the last day of the month
   */
  public void dayChange(boolean isLastDayOfMonth) {
    BigDecimal dayPercent = percent.divide(new BigDecimal(365), 10, RoundingMode.HALF_UP);
    accruals = accruals.add(
        balance.multiply(dayPercent).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));

    if (isLastDayOfMonth) {
      balance = balance.add(accruals);
      accruals = new BigDecimal(0);
    }
  }

  /**
   Checks if the requested amount can be withdrawn from the account.
   @param amount the amount to be withdrawn
   @return true if the withdrawal is allowed, false otherwise
   @throws BankAccountException if the requested amount is negative
   */
  public boolean isWithdrawAllowed(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    return (balance.subtract(amount)).compareTo(BigDecimal.ZERO) >= 0;
  }

  /**
   Adds the specified amount to the account balance.
   @param amount the amount to be added
   @throws BankAccountException if the specified amount is negative
   */
  public void topUp(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    balance = balance.add(amount);
  }

  /**
   Subtracts the specified amount from the account balance.
   @param amount the amount to be subtracted
   @throws BankAccountException if the specified amount is negative or exceeds the account balance
   */
  public void withdraw(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    balance = balance.subtract(amount);
  }
}

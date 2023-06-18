package is.technologies.models;

import is.technologies.entities.BankAccount;
import is.technologies.entities.*;
import is.technologies.exceptions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Calendar;
import lombok.Getter;

/**
 A class representing a deposit account in a bank.
 */
@Getter
public class DepositAccount implements BankAccount {

  /**The date when the loan must be fully repaid.*/
  private final Calendar dateOfDeadline;

  /**The date when the loan was issued.*/
  private final Calendar date;

  /**The interest rate of the loan in percent.*/
  private final BigDecimal percent;

  /**The current amount of accrued interest on the loan.*/
  private BigDecimal accruals;

  /**The current balance of the loan.*/
  private BigDecimal balance;

  /**The bank that issued the loan.*/
  private final Bank bank;

  /**The ID of the bank account associated with the loan.*/
  private final BankAccountId bankAccountId;

  /**The owner of the loan.*/
  private final Client owner;

  /**A boolean indicating whether the loan deadline has passed.*/
  private boolean isDeadlineEnd;

  /**
   Creates a new instance of the DepositAccount class with the specified parameters.
   @param accountId the unique identifier of the bank account
   @param owner the owner of the bank account
   @param percent the annual percentage yield (APY) of the deposit account
   @param date the date when the deposit account was created
   @param balance the initial balance of the deposit account
   @param bank the bank where the deposit account is held
   @throws BankAccountException if the percent is negative, owner is null, accountId is null,
   balance is negative, or bank is null
   */
  public DepositAccount(BankAccountId accountId, Client owner, BigDecimal percent, Calendar date, BigDecimal balance, Bank bank)
      throws BankAccountException {
    if (percent.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidPercent(percent);
    if (owner == null)
      throw BankAccountException.invalidClient();
    if (accountId == null)
      throw BankAccountException.invalidBankAccountId();
    if (balance.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidBalance(balance);
    if (bank == null)
      throw BankAccountException.invalidBank();

    this.balance = balance;
    this.bankAccountId = accountId;
    this.owner = owner;
    this.dateOfDeadline = date;
    this.percent = percent;
    this.isDeadlineEnd = false;
    this.accruals = BigDecimal.ZERO;
    this.bank = bank;
    this.date = Calendar.getInstance();
    this.date.setTime(new Date());
  }

  /**
   Returns the balance of this account.
   @return the balance of this account
   */
  public BigDecimal getLimit() {
    return balance;
  }

  /**
   This method represents a day change on a deposit account. It calculates the daily accrual of interest
   and updates the balance accordingly.
   @param isLastDayOfMonth a boolean indicating if the day being processed is the last day of the month.
   */
  public void dayChange(boolean isLastDayOfMonth) {
    date.add(Calendar.DATE, 1);
    if (dateOfDeadline == date) {
      isDeadlineEnd = true;
    }

    BigDecimal dayPercent = percent.divide(new BigDecimal(365), 10,  RoundingMode.HALF_UP);
    accruals = accruals.add(
        balance.multiply(dayPercent).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));

    if (isLastDayOfMonth && !isDeadlineEnd) {
      balance = balance.add(accruals);
      accruals = BigDecimal.ZERO;
    }
  }

  /**
   Determines whether a withdrawal is allowed based on the current state of the account.
   @param amount the amount of money to be withdrawn
   @return true if the withdrawal is allowed, false otherwise
   */
  public boolean isWithdrawAllowed(BigDecimal amount) {
    if (!isDeadlineEnd) return false;

    return balance.subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
  }

  /**
   Increases the balance of the account by the specified amount.
   @param amount the amount of money to be deposited
   @throws BankAccountException if the specified amount is negative
   */
  public void topUp(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    balance = balance.add(amount);
  }

  /**
   Decreases the balance of the account by the specified amount.
   @param amount the amount of money to be withdrawn
   @throws BankAccountException if the specified amount is negative or if the withdrawal would cause the balance to go below zero
   */
  public void withdraw(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    balance = balance.subtract(amount);
  }
}

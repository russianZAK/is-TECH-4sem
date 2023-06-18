package is.technologies.models;

import is.technologies.entities.BankAccount;
import is.technologies.entities.*;
import is.technologies.exceptions.*;
import java.math.BigDecimal;
import lombok.Getter;

/**
 Represents a credit account of a client in a bank, implementing the IBankAccount interface.
 */
@Getter
public class CreditAccount implements BankAccount {

  /**The commission rate for transactions performed on this bank account.*/
  private final BigDecimal commission;

  /**The current balance of this bank account.*/
  private BigDecimal balance;

  /**The current debt of this bank account, if any.*/
  private BigDecimal debt;

  /**The credit limit for this bank account, if any.*/
  private final BigDecimal creditLimit;

  /**The unique identifier of this bank account.*/
  private final BankAccountId bankAccountId;

  /**The client who owns this bank account.*/
  private final Client owner;

  /**The bank that this bank account belongs to.*/
  private final Bank bank;

  /**
   Constructs a new CreditAccount object with the specified parameters.
   @param accountId The unique identifier of the account
   @param owner The client who owns the account
   @param creditLimit The maximum amount of credit available to the account holder
   @param commission The commission charged by the bank for transactions made with this account
   @param bank The bank that manages the account
   @throws BankAccountException if the credit limit is negative, the owner is null,
   the account ID is null, the commission is negative or the bank is null
   */
  public CreditAccount(BankAccountId accountId, Client owner, BigDecimal creditLimit, BigDecimal commission, Bank bank)
      throws BankAccountException {
    if (creditLimit.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidCreditLimit(creditLimit);
    if (owner == null)
      throw BankAccountException.invalidClient();
    if (accountId == null)
      throw BankAccountException.invalidBankAccountId();
    if (commission.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidCommissionForCreditAccount(commission);
    if (bank == null)
      throw BankAccountException.invalidBank();

    balance = creditLimit;
    bankAccountId = accountId;
    this.owner = owner;
    this.commission = commission;
    this.creditLimit = creditLimit;
    this.bank = bank;
  }

  /**
   Returns the credit limit for this account.
   @return the credit limit for this account.
   */
  public BigDecimal getLimit() {
    return creditLimit;
  }

  /**
   Changes the account balance and debt based on whether it is the last day of the month or not.
   @param islastDayOfMonth true if it is the last day of the month, false otherwise
   */
  public void dayChange(boolean islastDayOfMonth) {
    if (balance.compareTo(creditLimit) < 0) {
      debt = debt.add(commission);
    }

    if (islastDayOfMonth) {
      balance = balance.subtract(debt);
      debt = BigDecimal.ZERO;
    }
  }

  /**
   This method checks whether withdrawing specified amount from the account is allowed.
   @param amount the amount of money to be withdrawn
   @return true if the withdrawal is allowed, false otherwise
   @throws BankAccountException if the specified amount is negative
   */
  public boolean isWithdrawAllowed(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);

    return (balance.subtract(amount)).compareTo(BigDecimal.ZERO) >= 0;
  }

  /**
   This method adds the specified amount of money to the account balance.
   @param amount the amount of money to be added
   @throws BankAccountException if the specified amount is negative
   */
  public void topUp(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    balance = balance.add(amount);
  }

  /**
   This method withdraws the specified amount of money from the account balance.
   @param amount the amount of money to be withdrawn
   @throws BankAccountException if the specified amount is negative or if the account balance is insufficient
   */
  public void withdraw(BigDecimal amount) throws BankAccountException {
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw BankAccountException.invalidAmountOfMoney(amount);
    balance = balance.subtract(amount);
  }
}

package is.technologies.entities;
import is.technologies.models.*;
import is.technologies.exceptions.BankAccountException;
import java.math.BigDecimal;

/**
 Represents a bank account interface.
 */
public interface BankAccount {
  /**
   Gets the balance of the bank account.
   @return The balance of the bank account.
   */
  BigDecimal getBalance();

  /**
   Gets the bank where the bank account is located.
   @return The bank where the bank account is located.
   */
  Bank getBank();

  /**
   Gets the unique ID of the bank account.
   @return The unique ID of the bank account.
   */
  BankAccountId getBankAccountId();

  /**
   Gets the owner of the bank account.
   @return The owner of the bank account.
   */
  Client getOwner();

  /**
   Performs end-of-day processing on the bank account.
   @param isLastDayOfMonth True if today is the last day of the month, false otherwise.
   */
  void dayChange(boolean isLastDayOfMonth);

  /**
   Deposits money into the bank account.
   @param amount The amount of money to deposit.
   @throws BankAccountException if there is an error while depositing the money.
   */
  void topUp(BigDecimal amount) throws BankAccountException;

  /**
   Withdraws money from the bank account.
   @param amount The amount of money to withdraw.
   @throws BankAccountException if there is an error while withdrawing the money.
   */
  void withdraw(BigDecimal amount) throws BankAccountException;

  /**
   Checks if a given amount can be withdrawn from the bank account.
   @param amount The amount of money to check for withdrawal.
   @return True if the amount can be withdrawn, false otherwise.
   @throws BankAccountException if there is an error while checking the withdrawal limit.
   */
  boolean isWithdrawAllowed(BigDecimal amount) throws BankAccountException;

  /**
   Gets the limit for the bank account.
   @return The limit for the bank account.
   */
  BigDecimal getLimit();
}

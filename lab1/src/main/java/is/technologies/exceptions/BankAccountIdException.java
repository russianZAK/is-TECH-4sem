package is.technologies.exceptions;


/**
 An exception class representing errors that may occur in the BankAccountId class.
 */
public class BankAccountIdException extends Exception{
  private BankAccountIdException(String message) {}

  public static BankAccountIdException invalidId(int id) {
    return new BankAccountIdException(id + "is invalid");
  }
}

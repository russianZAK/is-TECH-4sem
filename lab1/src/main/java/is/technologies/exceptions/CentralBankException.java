package is.technologies.exceptions;


/**
 An exception class representing errors that may occur in the CentralBank class.
 */
public class CentralBankException extends Exception{
  private CentralBankException(String message) {}

  public static CentralBankException invalidWatcher() {
    return new CentralBankException("watcher is invalid");
  }
}

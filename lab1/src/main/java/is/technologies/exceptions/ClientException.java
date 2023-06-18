package is.technologies.exceptions;


/**
 An exception class representing errors that may occur in the Client class.
 */
public class ClientException extends Exception{
  private ClientException(String message) {}

  public static ClientException invalidBankAccount() {
    return new ClientException("Bank account is invalid");
  }

  public static ClientException invalidBank() {
    return new ClientException("Bank is invalid");
  }

  public static ClientException invalidPayload() {
    return new ClientException("payload is invalid");
  }

  public static ClientException invalidData() {
    return new ClientException("data is invalid");
  }

  public static ClientException invalidMediator() {
    return new ClientException("mediator is invalid");
  }
}

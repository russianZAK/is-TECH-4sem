package is.technologies.exceptions;


/**
 An exception class representing errors that may occur in the Aggregator class.
 */
public class AggregatorException extends Exception {
  private AggregatorException(String message) {}

  public static AggregatorException invalidPayload() {
    return new AggregatorException("payload is invalid");
  }

  public static AggregatorException invalidObserver() {
    return new AggregatorException("observer is invalid");
  }
}

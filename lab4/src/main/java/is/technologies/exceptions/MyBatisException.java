package is.technologies.exceptions;

public class MyBatisException extends Exception{

  private MyBatisException(String message) {}

  public static MyBatisException MyBatisError() {
    return new MyBatisException("working with MyBatis caused an error");
  }
}

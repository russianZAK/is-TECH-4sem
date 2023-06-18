package is.technologies.exceptions;

public class JDBCException extends Exception{
  private JDBCException(String message) {}

  public static JDBCException JDBCerror() {
    return new JDBCException("working with JDBC caused an error");
  }
  public static JDBCException entityDoesNotExist() {
    return new JDBCException("entity does not exist");
  }
}

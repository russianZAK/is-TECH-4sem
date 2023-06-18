package is.technologies.dao.JDBC;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class provides utility methods for managing JDBC connections using HikariCP connection
 * pooling.
 */
public class JDBCUtil {

  private static HikariDataSource dataSource;

  private JDBCUtil() {
  }

  static {
    final HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/buildings_db");
    config.setUsername("postgres");
    config.setPassword("root");
    try {
      dataSource = new HikariDataSource(config);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns the HikariCP data source instance.
   * @return The HikariCP data source instance.
   */
  public static HikariDataSource getDataSource() {
    return dataSource;
  }

  /**
   * Returns a connection from the HikariCP connection pool.
   * @return A connection from the HikariCP connection pool.
   * @throws SQLException If an error occurs while attempting to retrieve a connection.
   */
  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}

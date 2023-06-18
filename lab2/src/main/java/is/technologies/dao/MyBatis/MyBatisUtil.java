package is.technologies.dao.MyBatis;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * A utility class for MyBatis configuration and initialization.
 */
public class MyBatisUtil {

  /**
   * The resource path for the MyBatis configuration file.
   */
  private static final String RESOURCE = "mybatis-config.xml";
  /**
   * The pre-configured MyBatis {@link SqlSessionFactory}.
   */
  private static final SqlSessionFactory sqlSessionFactory;

  /**
   * Private constructor to prevent instantiation.
   */
  private MyBatisUtil() {
  }

  static {
    try {
      final InputStream inputStream = Resources.getResourceAsStream(RESOURCE);
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns the pre-configured MyBatis {@link SqlSessionFactory}.
   * @return the pre-configured MyBatis {@link SqlSessionFactory}
   */
  public static SqlSessionFactory getSessionFactory() {
    return sqlSessionFactory;
  }
}

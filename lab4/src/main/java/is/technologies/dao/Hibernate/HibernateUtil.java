package is.technologies.dao.Hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


/**
 * The HibernateUtil class provides a singleton instance of Hibernate's SessionFactory.
 */
public class HibernateUtil {

  /**
   * The singleton instance of Hibernate's SessionFactory.
   */
  private static SessionFactory sessionFactory;

  /**
   * Private constructor to prevent instantiation from outside the class.
   */
  private HibernateUtil() {
  }

  static {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(
        "hibernate.cfg.xml").build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception e) {
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }

  /**
   * Returns the singleton instance of Hibernate's SessionFactory.
   * @return the singleton instance of Hibernate's SessionFactory
   */
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}

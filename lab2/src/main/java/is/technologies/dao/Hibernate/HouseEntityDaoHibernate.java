package is.technologies.dao.Hibernate;

import is.technologies.entities.HouseEntity;
import is.technologies.interfaces.Repository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * The HouseEntityDaoHibernate class is an implementation of the Repository interface for
 * HouseEntity using Hibernate.
 */
public class HouseEntityDaoHibernate implements Repository<HouseEntity> {

  /**
   * The singleton instance of Hibernate's SessionFactory.
   */
  private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  /**
   * Constructor for creating an instance of the HouseEntityDaoHibernate class.
   */
  public HouseEntityDaoHibernate() {
  }

  /**
   * Saves a HouseEntity object to the database.
   * @param entity the HouseEntity object to save
   * @return the saved HouseEntity object
   */
  @Override
  public HouseEntity save(HouseEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.save(entity);
    transaction.commit();
    return entity;
  }

  /**
   * Deletes a HouseEntity object from the database by its ID.
   * @param id the ID of the HouseEntity object to delete
   */
  @Override
  public void deleteById(long id) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    HouseEntity house = session.get(HouseEntity.class, (int) id);
    if (house != null) {
      session.delete(house);
      transaction.commit();
    }
  }

  /**
   * Deletes a HouseEntity object from the database.
   * @param entity the HouseEntity object to delete
   */
  @Override
  public void deleteByEntity(HouseEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.delete(entity);
    transaction.commit();
  }

  /**
   * Deletes all HouseEntity objects from the database.
   */
  @Override
  public void deleteAll() {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.createQuery("DELETE FROM HouseEntity ").executeUpdate();
    session.createNativeQuery("ALTER SEQUENCE house_id_seq RESTART WITH 1").executeUpdate();
    transaction.commit();
  }

  /**
   * Updates a HouseEntity object in the database.
   * @param entity the HouseEntity object to update
   * @return the updated HouseEntity object
   */
  @Override
  public HouseEntity update(HouseEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.update(entity);
    transaction.commit();
    return entity;
  }

  /**
   * Gets a HouseEntity object from the database by its ID.
   * @param id the ID of the HouseEntity object to get
   * @return the HouseEntity object with the specified ID
   */
  @Override
  public HouseEntity getById(long id) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    HouseEntity house = session.get(HouseEntity.class, (int) id);
    transaction.commit();
    return house;
  }

  /**
   * Gets a list of all HouseEntity objects from the database.
   * @return a list of all HouseEntity objects in the database
   */
  @Override
  public List<HouseEntity> getAll() {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    List<HouseEntity> list = session.createQuery("FROM HouseEntity").list();
    transaction.commit();
    return list;
  }
}


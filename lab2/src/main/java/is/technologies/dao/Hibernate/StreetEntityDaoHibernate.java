package is.technologies.dao.Hibernate;

import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import is.technologies.interfaces.Repository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Implementation of the Repository interface for StreetEntity using Hibernate.
 */
public class StreetEntityDaoHibernate implements Repository<StreetEntity> {

  /**
   * The Hibernate SessionFactory instance.
   */
  private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  /**
   * Default constructor for creating a new instance of the StreetEntityDaoHibernate.
   */
  public StreetEntityDaoHibernate() {
  }

  /**
   * Saves a StreetEntity instance to the database.
   * @param entity The StreetEntity instance to save.
   * @return The saved StreetEntity instance.
   */
  @Override
  public StreetEntity save(StreetEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.save(entity);
    transaction.commit();
    return entity;
  }

  /**
   * Deletes a StreetEntity instance from the database by its id.
   * @param id The id of the StreetEntity to delete.
   */
  @Override
  public void deleteById(long id) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    StreetEntity street = session.get(StreetEntity.class, (int) id);
    if (street != null) {
      session.delete(street);
      transaction.commit();
    }
  }

  /**
   * Deletes a StreetEntity instance from the database.
   * @param entity The StreetEntity instance to delete.
   */
  @Override
  public void deleteByEntity(StreetEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.delete(entity);
    transaction.commit();
  }

  /**
   * Deletes all StreetEntity instances from the database.
   */
  @Override
  public void deleteAll() {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.createQuery("DELETE FROM StreetEntity").executeUpdate();
    session.createNativeQuery("ALTER SEQUENCE street_id_seq RESTART WITH 1").executeUpdate();
    transaction.commit();
  }

  /**
   * Updates a StreetEntity instance in the database.
   * @param entity The StreetEntity instance to update.
   * @return The updated StreetEntity instance.
   */
  @Override
  public StreetEntity update(StreetEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.update(entity);
    transaction.commit();
    return entity;
  }

  /**
   * Retrieves a StreetEntity instance from the database by its id.
   * @param id The id of the StreetEntity to retrieve.
   * @return The retrieved StreetEntity instance.
   */
  @Override
  public StreetEntity getById(long id) {
    StreetEntity street = null;
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    street = session.get(StreetEntity.class, (int) id);
    transaction.commit();
    return street;
  }

  /**
   * Retrieves all StreetEntity instances from the database.
   * @return A List of all StreetEntity instances in the database.
   */
  @Override
  public List<StreetEntity> getAll() {
    List<StreetEntity> list = new ArrayList<>();
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    list = session.createQuery("FROM StreetEntity ").list();
    transaction.commit();
    return list;
  }

  /**
   * Retrieves the first 5 HouseEntity instances in the database that are associated with a given StreetEntity id.
   * @param id The id of the StreetEntity to retrieve the HouseEntity instances for.
   * @return A List of up to 5 HouseEntity instances associated with the given StreetEntity id.
   */
  public List<HouseEntity> getAllByVId(long id) {
    List<HouseEntity> listOfEntities = new ArrayList();
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    EntityManagerFactory entityManager = session.getEntityManagerFactory();
    String hql = "FROM HouseEntity WHERE streetId = :id";
    listOfEntities = entityManager
        .createEntityManager().createQuery(hql, HouseEntity.class)
        .setParameter("id", (int) id)
        .getResultList();
    transaction.commit();
    return listOfEntities.stream().limit(5).toList();
  }
}

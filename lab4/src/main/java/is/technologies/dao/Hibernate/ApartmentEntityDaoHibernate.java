package is.technologies.dao.Hibernate;

import is.technologies.entities.ApartmentEntity;
import is.technologies.interfaces.Repository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 Implementation of Repository interface for ApartmentEntity using Hibernate
 */
public class ApartmentEntityDaoHibernate implements Repository<ApartmentEntity> {

  private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  /**
   Default constructor
   */
  public ApartmentEntityDaoHibernate() {
  }

  /**
   Saves an ApartmentEntity instance to the database
   @param entity the entity to save
   @return the saved entity
   */
  @Override
  public ApartmentEntity save(ApartmentEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.save(entity);
    transaction.commit();
    return entity;
  }

  /**
   Deletes an ApartmentEntity instance from the database by its ID
   @param id the ID of the entity to delete
   */
  @Override
  public void deleteById(long id) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    ApartmentEntity apartment = session.get(ApartmentEntity.class, (int) id);
    if (apartment != null) {
      session.delete(apartment);
      transaction.commit();
    }
  }

  /**
   Deletes an ApartmentEntity instance from the database
   @param entity the entity to delete
   */
  @Override
  public void deleteByEntity(ApartmentEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.delete(entity);
    transaction.commit();
  }

  /**
   Deletes all ApartmentEntity instances from the database
   */
  @Override
  public void deleteAll() {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.createQuery("DELETE FROM ApartmentEntity ").executeUpdate();
    session.createNativeQuery("ALTER SEQUENCE apartment_id_seq RESTART WITH 1").executeUpdate();
    transaction.commit();
  }

  /**
   Updates an ApartmentEntity instance in the database
   @param entity the entity to update
   @return the updated entity
   */
  @Override
  public ApartmentEntity update(ApartmentEntity entity) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    session.update(entity);
    transaction.commit();
    return entity;
  }

  /**
   Retrieves an ApartmentEntity instance from the database by its ID
   @param id the ID of the entity to retrieve
   @return the retrieved entity
   */
  @Override
  public ApartmentEntity getById(long id) {
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    ApartmentEntity apartment = session.get(ApartmentEntity.class, (int) id);
    transaction.commit();
    return apartment;
  }

  /**
   Retrieves a list of all ApartmentEntity instances from the database
   @return a list of all ApartmentEntity instances
   */
  @Override
  public List<ApartmentEntity> getAll(){
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction = session.beginTransaction();
    List<ApartmentEntity> list = session.createQuery("FROM ApartmentEntity ").list();
    transaction.commit();
    return list;
  }

  /**
   Unsupported operation
   @throws UnsupportedOperationException
   */
  public List<ApartmentEntity> getAllByVId(long id) {
    throw new UnsupportedOperationException();
  }
}
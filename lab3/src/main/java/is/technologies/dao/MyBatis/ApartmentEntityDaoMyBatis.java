package is.technologies.dao.MyBatis;

import is.technologies.entities.ApartmentEntity;
import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import is.technologies.entities.mappers.ApartmentEntityMapper;
import is.technologies.entities.mappers.HouseEntityMapper;
import is.technologies.exceptions.JDBCException;
import is.technologies.interfaces.Repository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 Implementation of the {@link Repository} interface for the {@link ApartmentEntity} entity using MyBatis.
 */
public class ApartmentEntityDaoMyBatis implements Repository<ApartmentEntity> {

  /**
   Saves the given entity to the database using MyBatis.
   @param entity the entity to be saved
   @return the saved entity
   */
  @Override
  public ApartmentEntity save(ApartmentEntity entity) {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    mapper.save(entity);
    session.commit();
    session.close();
    return entity;
  }

  /**
   Deletes the entity with the given ID from the database using MyBatis.
   @param id the ID of the entity to be deleted
   */
  @Override
  public void deleteById(long id)  {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    mapper.deleteById(id);
    session.commit();
    session.close();
  }

  /**
   Deletes the given entity from the database using MyBatis.
   @param entity the entity to be deleted
   */
  @Override
  public void deleteByEntity(ApartmentEntity entity) {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    mapper.deleteByEntity(entity);
    session.commit();
    session.close();
  }

  /**
   Deletes all entities of this type from the database using MyBatis.
   */
  @Override
  public void deleteAll() {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    mapper.deleteAll();
    mapper.resetAutoIncrement();
    session.commit();
    session.close();
  }

  /**
   Updates the given entity in the database using MyBatis.
   @param entity the entity to be updated
   @return the updated entity
   */
  @Override
  public ApartmentEntity update(ApartmentEntity entity) {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    mapper.update(entity);
    session.commit();
    session.close();
    return entity;
  }

  /**
   Retrieves the entity with the given ID from the database using MyBatis.
   @param id the ID of the entity to be retrieved
   @return the retrieved entity
   @throws IOException if there is an I/O error
   @throws JDBCException if there is an error executing the SQL statement
   @throws SQLException if there is an error accessing the database
   */
  @Override
  public ApartmentEntity getById(long id) throws IOException, JDBCException, SQLException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    ApartmentEntity apartment = mapper.getById(id);
    HouseEntity house = mapper.getHouse(mapper.findHouseIdById(id));
    StreetEntity street = mapper.getStreet(mapper.findStreetIdById(house.getId()));
    house.setStreet(street);
    apartment.setHouse(house);
    session.commit();
    session.close();
    return apartment;
  }

  /**
   Retrieves all entities of this type from the database using MyBatis.
   @return a list of all entities of this type
   @throws IOException if there is an I/O error
   @throws JDBCException if there is an error executing the SQL statement
   @throws SQLException if there is an error accessing the database
   */
  @Override
  public List<ApartmentEntity> getAll() throws IOException, JDBCException, SQLException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    ApartmentEntityMapper mapper = session.getMapper(ApartmentEntityMapper.class);
    List<ApartmentEntity> list = mapper.getAll();

    for (ApartmentEntity apartment: list){
      HouseEntity house = mapper.getHouse(mapper.findHouseIdById(apartment.getId()));
      StreetEntity street = mapper.getStreet(mapper.findStreetIdById(house.getId()));
      house.setStreet(street);
      apartment.setHouse(house);
    }

    session.close();
    return list;
  }

  public List<ApartmentEntity> getAllByVId(long id) throws IOException {
    throw new UnsupportedOperationException();
  }
}

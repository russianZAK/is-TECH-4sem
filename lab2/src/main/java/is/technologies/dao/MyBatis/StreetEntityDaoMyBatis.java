package is.technologies.dao.MyBatis;

import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import is.technologies.entities.mappers.StreetEntityMapper;
import is.technologies.interfaces.Repository;
import java.io.IOException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 * Implementation of Repository interface for StreetEntity using MyBatis.
 */
public class StreetEntityDaoMyBatis implements Repository<StreetEntity> {

  /**
   * Saves a new street entity to the database.
   * @param entity the street entity to save
   * @return the saved street entity
   * @throws IOException if an I/O error occurs
   */
  @Override
  public StreetEntity save(StreetEntity entity) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    mapper.save(entity);
    session.commit();
    session.close();
    return entity;
  }

  /**
   * Deletes a street entity from the database by its ID.
   * @param id the ID of the street entity to delete
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void deleteById(long id) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    mapper.deleteById(id);
    session.commit();
    session.close();
  }

  /**
   * Deletes a street entity from the database.
   * @param entity the street entity to delete
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void deleteByEntity(StreetEntity entity) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    mapper.deleteByEntity(entity);
    session.commit();
    session.close();
  }

  /**
   * Deletes all street entities from the database and resets the auto-increment counter.
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void deleteAll() throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    mapper.deleteAll();
    mapper.resetAutoIncrement();
    session.commit();
    session.close();
  }

  /**
   * Updates a street entity in the database.
   * @param entity the street entity to update
   * @return the updated street entity
   * @throws IOException if an I/O error occurs
   */
  @Override
  public StreetEntity update(StreetEntity entity) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    mapper.update(entity);
    session.commit();
    session.close();
    return entity;
  }

  /**
   * Retrieves a street entity from the database by its ID.
   * @param id the ID of the street entity to retrieve
   * @return the retrieved street entity
   * @throws IOException if an I/O error occurs
   */
  @Override
  public StreetEntity getById(long id) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    StreetEntity street = mapper.getById(id);
    session.commit();
    session.close();
    return street;
  }

  /**
   * Retrieves all street entities from the database.
   * @return a list of all street entities
   * @throws IOException if an I/O error occurs
   */
  @Override
  public List<StreetEntity> getAll() throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    List<StreetEntity> list = mapper.getAll();
    session.close();
    return list;
  }

  /**
   * Retrieves a list of HouseEntities by StreetId from the database using MyBatis.
   * @param id the ID of the StreetId to retrieve the houses from
   * @return a List of HouseEntity objects associated with the given StreetId
   */
  public List<HouseEntity> getAllByVId(long id) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    StreetEntityMapper mapper = session.getMapper(StreetEntityMapper.class);
    List<HouseEntity> houseEntities = mapper.getAllByVId(id);
    StreetEntity street = getById(id);
    for (HouseEntity houseEntity : houseEntities) {
      houseEntity.setStreet(street);
    }
    return houseEntities.stream().limit(5).toList();
  }
}

package is.technologies.dao.MyBatis;

import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import is.technologies.entities.mappers.HouseEntityMapper;
import is.technologies.interfaces.Repository;
import java.io.IOException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 * A DAO implementation for {@link HouseEntity} using MyBatis.
 */
public class HouseEntityDaoMyBatis implements Repository<HouseEntity> {

  /**
   * Saves a new house entity to the database.
   * @param entity the house entity to be saved
   * @return the saved house entity
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public HouseEntity save(HouseEntity entity) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    mapper.save(entity);
    session.commit();
    session.close();
    return entity;
  }

  /**
   * Deletes a house entity from the database by ID.
   * @param id the ID of the house entity to be deleted
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public void deleteById(long id) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    mapper.deleteById(id);
    session.commit();
    session.close();
  }

  /**
   * Deletes a house entity from the database.
   * @param entity the house entity to be deleted
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public void deleteByEntity(HouseEntity entity) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    mapper.deleteByEntity(entity);
    session.commit();
    session.close();
  }

  /**
   * Deletes all house entities from the database.
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public void deleteAll() throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    mapper.deleteAll();
    mapper.resetAutoIncrement();
    session.commit();
    session.close();
  }

  /**
   * Updates an existing house entity in the database.
   * @param entity the updated house entity
   * @return the updated house entity
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public HouseEntity update(HouseEntity entity) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    mapper.update(entity);
    session.commit();
    session.close();
    return entity;
  }

  /**
   * Retrieves a house entity from the database by ID.
   * @param id the ID of the house entity to retrieve
   * @return the retrieved house entity
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public HouseEntity getById(long id) throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    HouseEntity house = mapper.getById(id);
    StreetEntity street = mapper.getStreet(mapper.findStreetIdById(id));
    house.setStreet(street);
    session.commit();
    session.close();
    return house;
  }

  /**
   * Retrieves all house entities from the database.
   * @return a list of all house entities in the database
   * @throws IOException if there is an I/O error while communicating with the database
   */
  @Override
  public List<HouseEntity> getAll() throws IOException {
    SqlSession session = MyBatisUtil.getSessionFactory().openSession();
    HouseEntityMapper mapper = session.getMapper(HouseEntityMapper.class);
    List<HouseEntity> list = mapper.getAll();

    for (HouseEntity house: list){
      house.setStreet(mapper.getStreet(mapper.findStreetIdById(house.getId())));
    }
    session.close();
    return list;
  }
}

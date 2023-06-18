package is.technologies.dao.JDBC;

import is.technologies.entities.ApartmentEntity;
import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import is.technologies.exceptions.JDBCException;
import is.technologies.interfaces.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The JDBC implementation of the {@link Repository} interface for {@link HouseEntity}.
 */
public class HouseEntityDaoJDBC implements Repository<HouseEntity> {

  /**
   * The database connection used by this DAO.
   */
  private final Connection connection = JDBCUtil.getConnection();

  /**
   * Creates a new instance of the {@code HouseEntityDaoJDBC} class.
   * @throws SQLException if there is an error initializing the database connection
   */
  public HouseEntityDaoJDBC() throws SQLException {
  }

  /**
   * Inserts a new {@link HouseEntity} into the database.
   * @param entity the entity to save
   * @return the saved entity
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public HouseEntity save(HouseEntity entity) throws JDBCException, SQLException {
    String sql = "INSERT INTO house (id, name, build_date, num_floors, type, material, street_id)" +
        "VALUES(?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, entity.getId());
    statement.setString(2, entity.getName());
    statement.setDate(3, entity.getBuildDate());
    statement.setLong(4, entity.getNumFloors());
    statement.setString(5, entity.getType().toString());
    statement.setString(6, entity.getMaterial());
    statement.setLong(7, entity.getStreet().getId());
    statement.execute();
    return entity;
  }

  /**
   * Deletes a {@link HouseEntity} from the database by its ID.
   * @param id the ID of the entity to delete
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public void deleteById(long id) throws JDBCException, SQLException {
    String sql = "DELETE FROM house WHERE id = " + id;
    Statement statement = connection.createStatement();
    statement.execute(sql);
  }

  /**
   * Deletes a {@link HouseEntity} from the database.
   * @param entity the entity to delete
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public void deleteByEntity(HouseEntity entity) throws JDBCException, SQLException {
    deleteById(entity.getId());
  }

  /**
   * Deletes all {@link HouseEntity} objects from the database.
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public void deleteAll() throws JDBCException, SQLException {
    String sql = "DELETE FROM house";
    Statement statement = connection.createStatement();
    statement.execute(sql);
    sql = "ALTER SEQUENCE house_id_seq RESTART WITH 1";
    statement.execute(sql);
  }

  /**
   * Updates an existing {@link HouseEntity} in the database.
   * @param entity the entity to update
   * @return the updated entity
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public HouseEntity update(HouseEntity entity) throws JDBCException, SQLException {
    String sql = "UPDATE house SET name = ?,  build_date = ?, num_floors = ?, type = ?, material = ?, street_id = ? WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, entity.getName());
    statement.setDate(2, entity.getBuildDate());
    statement.setLong(3, entity.getNumFloors());
    statement.setString(4, entity.getType().toString());
    statement.setString(5, entity.getMaterial());
    statement.setLong(6, entity.getStreet().getId());
    statement.setLong(7, entity.getId());
    statement.execute();
    return entity;
  }

  /**
   * Retrieves a {@link HouseEntity} from the database by its ID.
   * @param id the ID of the entity to retrieve
   * @return the retrieved entity
   * @throws JDBCException if the entity does not exist in the database
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public HouseEntity getById(long id) throws JDBCException, SQLException {
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM house WHERE id = " + id);
    if (resultSet.next()) {
      HouseEntity houseEntity = new HouseEntity();
      houseEntity.setId(resultSet.getLong("id"));
      houseEntity.setName(resultSet.getString("name"));
      houseEntity.setBuildDate(resultSet.getDate("build_date"));
      houseEntity.setNumFloors(resultSet.getInt("num_floors"));
      houseEntity.setType(resultSet.getString("type"));
      houseEntity.setMaterial(resultSet.getString("material"));
      StreetEntity street = getStreet(resultSet.getLong("street_id"));
      houseEntity.setStreet(street);
      return houseEntity;
    }
    throw JDBCException.entityDoesNotExist();
  }

  /**
   * Retrieves a list of all {@link HouseEntity} objects from the database.
   * @return the list of all entities
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error with the database connection
   */
  @Override
  public List<HouseEntity> getAll() throws JDBCException, SQLException {
    List<HouseEntity> listOfEntities = new ArrayList();
    String sql = "SELECT * FROM house";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(sql);
    while (resultSet.next()) {
      HouseEntity entity = new HouseEntity();
      entity.setId(resultSet.getLong("id"));
      entity.setName(resultSet.getString("name"));
      entity.setBuildDate(resultSet.getDate("build_date"));
      entity.setNumFloors(resultSet.getInt("num_floors"));
      entity.setType(resultSet.getString("type"));
      entity.setMaterial(resultSet.getString("material"));
      StreetEntity street = getStreet(resultSet.getLong("street_id"));
      entity.setStreet(street);
      listOfEntities.add(entity);
    }
    return listOfEntities;
  }

  /**
   Returns a list of all apartment entities in the database that belong to the house with the given id.
   The list is limited to a maximum of 5 entities.
   @param id the id of the house to get apartments for
   @return a list of apartment entities belonging to the house with the given id
   @throws SQLException if there is an error accessing the database
   @throws JDBCException if there is an error with the JDBC driver
   */
  public List<ApartmentEntity> getAllByVId(long id) throws SQLException, JDBCException {
    List<ApartmentEntity> listOfEntities = new ArrayList();
    String sql = "SELECT * FROM apartment WHERE house_id = " + id;
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(sql);
    while (resultSet.next()) {
      ApartmentEntity entity = new ApartmentEntity();
      entity.setId(resultSet.getLong("id"));
      entity.setNumber(resultSet.getInt("number"));
      entity.setSquare(resultSet.getInt("square"));
      entity.setNumRooms(resultSet.getInt("num_rooms"));
      HouseEntity house = getById(resultSet.getLong("house_id"));
      entity.setHouse(house);
      listOfEntities.add(entity);
    }
    return listOfEntities.stream().limit(5).toList();
  }

  /**
   Returns the street entity with the given id from the database.
   @param id the id of the street to retrieve
   @return the street entity with the given id
   @throws SQLException if there is an error accessing the database
   @throws JDBCException if there is an error with the JDBC driver
   @throws JDBCException.entityDoesNotExist() if there is no street entity with the given id in the database
   */
  private StreetEntity getStreet(long id) throws SQLException, JDBCException {
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM street WHERE id = " + id);
    if (resultSet.next()) {
      StreetEntity streetEntity = new StreetEntity();
      streetEntity.setId(resultSet.getLong("id"));
      streetEntity.setName(resultSet.getString("name"));
      streetEntity.setPostalCode(resultSet.getLong("postal_code"));
      return streetEntity;
    }
    throw JDBCException.entityDoesNotExist();
  }
}

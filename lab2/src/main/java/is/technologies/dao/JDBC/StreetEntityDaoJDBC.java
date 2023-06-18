package is.technologies.dao.JDBC;

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
 * This class represents a Data Access Object (DAO) for the StreetEntity entity. It provides methods
 * to perform CRUD (Create, Read, Update, Delete) operations on the street table in the database.
 */
public class StreetEntityDaoJDBC implements Repository<StreetEntity> {

  /**
   * The connection to the database obtained from the JDBCUtil class.
   */
  private final Connection connection = JDBCUtil.getConnection();

  /**
   * Constructs a new StreetEntityDaoJDBC object.
   * @throws SQLException if a database access error occurs.
   */
  public StreetEntityDaoJDBC() throws SQLException {
  }

  /**
   * Saves a StreetEntity object to the database.
   * @param entity the StreetEntity object to save.
   * @return the saved StreetEntity object.
   * @throws JDBCException if an error occurs during the database operation.
   * @throws SQLException  if a database access error occurs.
   */
  @Override
  public StreetEntity save(StreetEntity entity) throws JDBCException, SQLException {
    String sql = "INSERT INTO street (id, name, postal_code)" +
        "VALUES(?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, entity.getId());
    statement.setString(2, entity.getName());
    statement.setLong(3, entity.getPostalCode());
    statement.execute();
    return entity;
  }

  /**
   * Deletes a StreetEntity object from the database by id.
   * @param id the id of the StreetEntity object to delete.
   * @throws JDBCException if an error occurs during the database operation.
   * @throws SQLException  if a database access error occurs.
   */
  @Override
  public void deleteById(long id) throws JDBCException, SQLException {
    String sql = "DELETE FROM street WHERE id = " + id;
    Statement statement = connection.createStatement();
    statement.execute(sql);
  }

  /**
   * Deletes a StreetEntity object from the database.
   * @param entity the StreetEntity object to delete.
   * @throws JDBCException if an error occurs during the database operation.
   * @throws SQLException  if a database access error occurs.
   */
  @Override
  public void deleteByEntity(StreetEntity entity) throws JDBCException, SQLException {
    deleteById(entity.getId());
  }

  /**
   * Deletes all StreetEntity objects from the database.
   * @throws JDBCException if an error occurs during the database operation.
   * @throws SQLException  if a database access error occurs.
   */
  @Override
  public void deleteAll() throws JDBCException, SQLException {
    String sql = "DELETE FROM street";
    Statement statement = connection.createStatement();
    statement.execute(sql);
    sql = "ALTER SEQUENCE street_id_seq RESTART WITH 1;";
    statement.execute(sql);
  }

  /**
   * Updates a StreetEntity object in the database.
   * @param entity the StreetEntity object to update.
   * @return the updated StreetEntity object.
   * @throws JDBCException if an error occurs during the database operation.
   * @throws SQLException  if a database access error occurs.
   */
  @Override
  public StreetEntity update(StreetEntity entity) throws JDBCException, SQLException {
    String sql = "UPDATE street SET name = ?, postal_code = ? WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, entity.getName());
    statement.setLong(2, entity.getPostalCode());
    statement.setLong(3, entity.getId());
    statement.execute();
    return entity;
  }

  /**
   * Retrieves a {@link StreetEntity} object from the database by its ID.
   * @param id the ID of the street entity to retrieve
   * @return the {@link StreetEntity} object with the given ID
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error accessing the database
   */
  @Override
  public StreetEntity getById(long id) throws JDBCException, SQLException {
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

  /**
   * Retrieves a list of all {@link StreetEntity} objects from the database.
   * @return a list of all {@link StreetEntity} objects
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error accessing the database
   */
  @Override
  public List<StreetEntity> getAll() throws JDBCException, SQLException {
    List<StreetEntity> listOfEntites = new ArrayList();
    String sql = "SELECT * FROM street";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(sql);

    while (resultSet.next()) {
      StreetEntity entity = new StreetEntity();
      entity.setId(resultSet.getLong("id"));
      entity.setName(resultSet.getString("name"));
      entity.setPostalCode(resultSet.getLong("postal_code"));
      listOfEntites.add(entity);
    }
    return listOfEntites;
  }

  /**
   * Retrieves a list of up to five {@link HouseEntity} objects associated with a given street ID.
   * @param id the ID of the street to retrieve house entities for
   * @return a list of up to five {@link HouseEntity} objects associated with the given street ID
   * @throws JDBCException if there is an error executing the SQL statement
   * @throws SQLException  if there is an error accessing the database
   */
  public List<HouseEntity> getAllByVId(long id) throws JDBCException, SQLException {
    List<HouseEntity> listOfEntities = new ArrayList();
    String sql = "SELECT * FROM house WHERE street_id = " + id;
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
      StreetEntity street = getById(resultSet.getLong("street_id"));
      entity.setStreet(street);
      listOfEntities.add(entity);
    }
    return listOfEntities.stream().limit(5).toList();
  }

}

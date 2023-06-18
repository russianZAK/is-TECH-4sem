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
 This class represents a data access object (DAO) for ApartmentEntity using JDBC.
 It implements the Repository interface to provide CRUD operations for ApartmentEntity.
 The class uses a Connection object to interact with a PostgreSQL database.
 */
public class ApartmentEntityDaoJDBC implements Repository<ApartmentEntity> {

  /**
   The Connection object used to interact with the database.
   */
  private final Connection connection = JDBCUtil.getConnection();

  /**
   Constructs a new ApartmentEntityDaoJDBC object.
   @throws SQLException if a database access error occurs
   */
  public ApartmentEntityDaoJDBC() throws SQLException {
  }

  /**
   Saves an ApartmentEntity to the database.
   @param entity the ApartmentEntity to save
   @return the saved ApartmentEntity
   @throws SQLException if a database access error occurs
   */
  @Override
  public ApartmentEntity save(ApartmentEntity entity) throws SQLException {
    String sql = "INSERT INTO apartment (id, number, square, num_rooms, house_id)" +
        "VALUES(?, ?, ?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, entity.getId());
    statement.setLong(2, entity.getNumber());
    statement.setLong(3, entity.getSquare());
    statement.setLong(4, entity.getNumRooms());
    statement.setLong(5, entity.getHouse().getId());
    statement.execute();
    return entity;
  }

  /**
   Deletes an ApartmentEntity from the database by its ID.
   @param id the ID of the ApartmentEntity to delete
   @throws SQLException if a database access error occurs
   */
  @Override
  public void deleteById(long id) throws SQLException {
    String sql = "DELETE FROM apartment WHERE id = " + id;
    Statement statement = connection.createStatement();
    statement.execute(sql);
  }

  /**
   Deletes an ApartmentEntity from the database.
   @param entity the ApartmentEntity to delete
   @throws SQLException if a database access error occurs
   */
  @Override
  public void deleteByEntity(ApartmentEntity entity) throws SQLException {
    deleteById(entity.getId());
  }

  /**
   Deletes all ApartmentEntities from the database.
   @throws SQLException if a database access error occurs
   */
  @Override
  public void deleteAll() throws SQLException {
    String sql = "DELETE FROM apartment";
    Statement statement = connection.createStatement();
    statement.execute(sql);
    sql = "ALTER SEQUENCE apartment_id_seq RESTART WITH 1";
    statement.execute(sql);
  }

  /**
   Updates an ApartmentEntity in the database.
   @param entity the ApartmentEntity to update
   @return the updated ApartmentEntity
   @throws SQLException if a database access error occurs
   */
  @Override
  public ApartmentEntity update(ApartmentEntity entity) throws SQLException {
    String sql = "UPDATE apartment SET number = ?,  square = ?, num_rooms = ?, house_id = ? WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, entity.getNumber());
    statement.setLong(2, entity.getSquare());
    statement.setLong(3, entity.getNumRooms());
    statement.setLong(4, entity.getHouse().getId());
    statement.setLong(5, entity.getId());
    statement.execute();
    return entity;
  }

  /**
   Gets an ApartmentEntity from the database by its ID.
   @param id the ID of the ApartmentEntity to get
   @return the ApartmentEntity with the specified ID
   @throws SQLException if a database access error occurs
   @throws JDBCException if the entity does not exist in the database
   */
  @Override
  public ApartmentEntity getById(long id) throws SQLException, JDBCException {
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM apartment WHERE id = " + id);
    if (resultSet.next()) {
      ApartmentEntity apartment = new ApartmentEntity();
      apartment.setId(resultSet.getLong(1));
      apartment.setNumber(resultSet.getInt(2));
      apartment.setSquare(resultSet.getInt(3));
      apartment.setNumRooms(resultSet.getInt(4));
      HouseEntity house = getHouse(resultSet.getLong("house_id"));
      apartment.setHouse(house);
      return apartment;
    }
    throw JDBCException.entityDoesNotExist();
  }

  /**
   Retrieves all apartment entities from the database.
   @return a list of ApartmentEntity objects
   @throws SQLException if there is an error executing the SQL statement
   @throws JDBCException if there is a general JDBC exception
   */
  @Override
  public List<ApartmentEntity> getAll() throws SQLException, JDBCException {
    List<ApartmentEntity> listOfEntities = new ArrayList();
    String sql = "SELECT * FROM apartment";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(sql);
    while (resultSet.next()) {
      ApartmentEntity entity = new ApartmentEntity();
      entity.setId(resultSet.getLong("id"));
      entity.setNumber(resultSet.getInt("number"));
      entity.setSquare(resultSet.getInt("square"));
      entity.setNumRooms(resultSet.getInt("num_rooms"));
      HouseEntity house = getHouse(resultSet.getLong("house_id"));
      entity.setHouse(house);
      listOfEntities.add(entity);
    }
    return listOfEntities;
  }

  public List<ApartmentEntity> getAllByVId(long id) {
    throw new UnsupportedOperationException();
  }

  /**
   Retrieves the house entity associated with the specified ID from the database.
   @param id the ID of the house to retrieve
   @return a HouseEntity object
   @throws SQLException if there is an error executing the SQL statement
   @throws JDBCException if there is a general JDBC exception
   */
  private HouseEntity getHouse(long id) throws SQLException, JDBCException {
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM house WHERE id = " + id);
    if (resultSet.next()) {
      HouseEntity entity = new HouseEntity();
      entity.setId(resultSet.getLong("id"));
      entity.setName(resultSet.getString("name"));
      entity.setBuildDate(resultSet.getDate("build_date"));
      entity.setNumFloors(resultSet.getInt("num_floors"));
      entity.setType(resultSet.getString("type"));
      entity.setMaterial(resultSet.getString("material"));
      StreetEntity street = getStreet(resultSet.getLong("street_id"));
      entity.setStreet(street);
      return entity;
    }
    throw JDBCException.entityDoesNotExist();
  }

  /**
   Retrieves the street entity associated with the specified ID from the database.
   @param id the ID of the street to retrieve
   @return a StreetEntity object
   @throws SQLException if there is an error executing the SQL statement
   @throws JDBCException if there is a general JDBC exception
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

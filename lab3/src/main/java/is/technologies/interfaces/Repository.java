package is.technologies.interfaces;

import is.technologies.exceptions.JDBCException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * The Repository interface represents a generic interface for database repositories. It defines
 * basic CRUD operations on entities of type T.
 * @param <T> the type of entity to be stored in the repository
 */
public interface Repository<T> {

  /**
   * Saves the given entity to the database.
   * @param entity the entity to be saved
   * @return the saved entity
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public T save(T entity) throws IOException, JDBCException, SQLException;

  /**
   * Deletes the entity with the given ID from the database.
   * @param id the ID of the entity to be deleted
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public void deleteById(long id) throws IOException, JDBCException, SQLException;

  /**
   * Deletes the given entity from the database.
   * @param entity the entity to be deleted
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public void deleteByEntity(T entity) throws IOException, JDBCException, SQLException;

  /**
   * Deletes all entities from the database.
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public void deleteAll() throws IOException, JDBCException, SQLException;

  /**
   * Updates the given entity in the database.
   * @param entity the entity to be updated
   * @return the updated entity
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public T update(T entity) throws IOException, JDBCException, SQLException;

  /**
   * Retrieves the entity with the given ID from the database.
   * @param id the ID of the entity to be retrieved
   * @return the retrieved entity
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public T getById(long id) throws IOException, JDBCException, SQLException;

  /**
   * Retrieves a list of all entities from the database.
   * @return a list of all entities in the repository
   * @throws IOException   if an I/O error occurs while accessing the database
   * @throws JDBCException if an error occurs while executing the JDBC operation
   * @throws SQLException  if a database access error occurs
   */
  public List<T> getAll() throws IOException, JDBCException, SQLException;
}

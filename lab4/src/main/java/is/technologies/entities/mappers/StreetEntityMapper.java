package is.technologies.entities.mappers;

import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Mapper interface for performing CRUD operations on StreetEntity using MyBatis.
 */
public interface StreetEntityMapper {
  /**
   * Inserts a new StreetEntity into the database.
   * @param entity the StreetEntity to be inserted.
   */
  @Insert("INSERT INTO street (id, name, postal_code) " +
      "VALUES (#{id}, #{name}, #{postalCode})")
  void save(StreetEntity entity);

  /**
   * Deletes a HouseEntity from the database by its ID.
   * @param id the ID of the HouseEntity to be deleted.
   */
  @Delete("DELETE FROM street WHERE id = #{id}")
  void deleteById(@Param("id") long id);

  /**
   * Deletes a HouseEntity from the database.
   * @param entity the HouseEntity to be deleted.
   */
  @Delete("DELETE FROM street WHERE id = #{id}")
  void deleteByEntity(StreetEntity entity);

  /**
   * Deletes all HouseEntities from the database.
   */
  @Delete("DELETE FROM street")
  void deleteAll();

  /**
   * Resets the auto-increment counter for the HouseEntity ID.
   */
  @Update("ALTER SEQUENCE street_id_seq RESTART WITH 1")
  void resetAutoIncrement();

  /**
   * Updates a HouseEntity in the database.
   * @param entity the HouseEntity to be updated.
   */
  @Update("UPDATE street SET name = #{name}, postal_code = #{postalCode} WHERE id = #{id}")
  void update(StreetEntity entity);

  /**
   * Retrieves a HouseEntity from the database by its ID.
   * @param id the ID of the HouseEntity to be retrieved.
   * @return the retrieved HouseEntity, or null if not found.
   */
  @Select("SELECT * FROM street WHERE id = #{id}")
  @Results({
      @Result(property = "postalCode", column = "postal_code")
  })
  StreetEntity getById(@Param("id") long id);

  /**
   * Retrieves all HouseEntities from the database.
   * @return a list of all retrieved HouseEntities.
   */
  @Select("SELECT * FROM street")
  @Results({
      @Result(property = "postalCode", column = "postal_code")
  })
  List<StreetEntity> getAll();

  /**
   * Retrieves a list of HouseEntities by StreetId from the database using MyBatis.
   * @param id the ID of the street to retrieve the houses from
   * @return a List of HouseEntity objects associated with the given StreetId
   */
  @Select("SELECT * FROM house WHERE street_id = #{id}")
  @Results({
      @Result(property = "buildDate", column = "build_date"),
      @Result(property = "numFloors", column = "num_floors")
  })
  List<HouseEntity> getAllByVId(@Param("id") long id);
}

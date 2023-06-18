package is.technologies.entities.mappers;

import is.technologies.entities.ApartmentEntity;
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
 * Mapper interface for performing CRUD operations on HouseEntity using MyBatis.
 */
public interface HouseEntityMapper {

  /**
   * Inserts a new HouseEntity into the database.
   * @param entity the HouseEntity to be inserted.
   */
  @Insert("INSERT INTO house (id, name, build_date, num_floors, type, material, street_id) " +
      "VALUES (#{id}, #{name}, #{buildDate}, #{numFloors}, #{type}, #{material}, #{street.id})")
  void save(HouseEntity entity);

  /**
   * Deletes a HouseEntity from the database by its ID.
   * @param id the ID of the HouseEntity to be deleted.
   */
  @Delete("DELETE FROM house WHERE id = #{id}")
  void deleteById(@Param("id") long id);

  /**
   * Deletes a HouseEntity from the database.
   * @param entity the HouseEntity to be deleted.
   */
  @Delete("DELETE FROM house WHERE id = #{id}")
  void deleteByEntity(HouseEntity entity);

  /**
   * Deletes all HouseEntities from the database.
   */
  @Delete("DELETE FROM house")
  void deleteAll();

  /**
   * Resets the auto-increment counter for the HouseEntity ID.
   */
  @Update("ALTER SEQUENCE house_id_seq RESTART WITH 1")
  void resetAutoIncrement();

  /**
   * Updates a HouseEntity in the database.
   * @param entity the HouseEntity to be updated.
   */
  @Update(
      "UPDATE house SET name = #{name}, build_date = #{buildDate}, num_floors = #{numFloors}, " +
          "type = #{type}, material = #{material}, street_id = #{streetId} WHERE id = #{id}")
  void update(HouseEntity entity);

  /**
   * Retrieves a HouseEntity from the database by its ID.
   * @param id the ID of the HouseEntity to be retrieved.
   * @return the retrieved HouseEntity, or null if not found.
   */
  @Select("SELECT * FROM house WHERE id = #{id}")
  @Results({
      @Result(property = "buildDate", column = "build_date"),
      @Result(property = "numFloors", column = "num_floors")
  })
  HouseEntity getById(@Param("id") long id);

  /**
   * Retrieves all HouseEntities from the database.
   * @return a list of all retrieved HouseEntities.
   */
  @Select("SELECT * FROM house")
  @Results({
      @Result(property = "buildDate", column = "build_date"),
      @Result(property = "numFloors", column = "num_floors")
  })
  List<HouseEntity> getAll();

  @Select("SELECT street_id FROM house WHERE street_id = #{id}")
  Long findStreetIdById(@Param("id") Long id);

  @Select("SELECT * FROM street WHERE id = #{id}")
  @Results({
      @Result(property = "postalCode", column = "postal_code")
  })
  StreetEntity getStreet(@Param("id") long id);

  @Select("SELECT * FROM apartment WHERE house_id = #{id}")
  @Results({
      @Result(property = "numRooms", column = "num_rooms")
  })
  List<ApartmentEntity> getAllByVId(@Param("id") long id);
}

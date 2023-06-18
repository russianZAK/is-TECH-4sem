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

public interface ApartmentEntityMapper {

  @Insert("INSERT INTO apartment (id, number, square, num_rooms, house_id) " +
      "VALUES (#{id}, #{number}, #{square}, #{numRooms}, #{house.id})")
  void save(ApartmentEntity entity);

  @Delete("DELETE FROM apartment WHERE id = #{id}")
  void deleteById(@Param("id") long id);

  @Delete("DELETE FROM apartment WHERE id = #{id}")
  void deleteByEntity(ApartmentEntity entity);

  @Delete("DELETE FROM apartment")
  void deleteAll();

  @Update("ALTER SEQUENCE apartment_id_seq RESTART WITH 1")
  void resetAutoIncrement();

  @Update(
      "UPDATE house SET number = #{number}, square = #{square}, num_rooms = #{numRooms}, " +
          "house_id = #{houseId} WHERE id = #{id}")
  void update(ApartmentEntity entity);

  @Select("SELECT * FROM apartment WHERE id = #{id}")
  @Results({
      @Result(property = "numRooms", column = "num_rooms")
  })
  ApartmentEntity getById(@Param("id") long id);

  @Select("SELECT * FROM apartment")
  @Results({
      @Result(property = "numRooms", column = "num_rooms")
  })
  List<ApartmentEntity> getAll();

  @Select("SELECT house_id FROM apartment WHERE id = #{id}")
  Long findHouseIdById(@Param("id") Long id);

  @Select("SELECT * FROM house WHERE id = #{id}")
  @Results({
      @Result(property = "buildDate", column = "build_date"),
      @Result(property = "numFloors", column = "num_floors")
  })
  HouseEntity getHouse(@Param("id") long id);

  @Select("SELECT street_id FROM house WHERE id = #{id}")
  Long findStreetIdById(@Param("id") Long id);

  @Select("SELECT * FROM street WHERE id = #{id}")
  @Results({
      @Result(property = "postalCode", column = "postal_code")
  })
  StreetEntity getStreet(@Param("id") long id);

  default List<ApartmentEntity> getAllByVId(long id) {
    throw new UnsupportedOperationException();
  }

}

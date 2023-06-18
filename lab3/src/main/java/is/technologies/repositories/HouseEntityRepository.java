package is.technologies.repositories;


import is.technologies.entities.ApartmentEntity;
import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing HouseEntity instances.
 */
@Repository
public interface HouseEntityRepository extends JpaRepository<HouseEntity, Long> {

  /**
   * Retrieves a list of houses by their street ID.
   * @param id The ID of the street.
   * @return The list of houses on the given street.
   */
  List<HouseEntity> findByStreetId(Long id);

  /**
   * Retrieves the house that contains a given apartment.
   * @param apartmentId The ID of the apartment.
   * @return The house that contains the given apartment.
   */
  @Query(value = "SELECT h.* FROM house h INNER JOIN apartment a ON h.id = a.house_id WHERE a.id = :apartmentId", nativeQuery = true)
  HouseEntity findByApartmentId(@Param("apartmentId") Long apartmentId);
}

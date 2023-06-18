package is.technologies.repositories;


import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This interface serves as a Spring Data JPA repository for {@link StreetEntity} domain objects.
 */
@Repository
public interface StreetEntityRepository extends JpaRepository<StreetEntity, Long> {

  /**
   * Retrieves a {@link StreetEntity} by the ID of the {@link HouseEntity} it belongs to.
   * @param houseId the ID of the {@link HouseEntity} to find the {@link StreetEntity} for
   * @return the {@link StreetEntity} for the specified {@link HouseEntity} ID, or {@code null} if
   * not found
   */
  @Query(value = "SELECT h.* FROM street h INNER JOIN house a ON h.id = a.street_id WHERE a.id = :houseId", nativeQuery = true)
  StreetEntity findByHouseId(@Param("houseId") Long houseId);
}

package is.technologies.repositories;

import is.technologies.entities.ApartmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 Repository interface for managing ApartmentEntity persistence in the database using Spring Data JPA.
 */
@Repository
public interface ApartmentEntityRepository extends JpaRepository<ApartmentEntity, Long> {
  /**
   Retrieves a list of ApartmentEntities by the ID of their associated HouseEntity.
   @param id the ID of the HouseEntity to filter by
   @return a list of ApartmentEntities associated with the given HouseEntity ID
   */
  List<ApartmentEntity> findByHouseId(Long id);

  /**
   * Retrieves a list of ApartmentEntities by the ID of their associated HouseEntity's street.
   * @param streetId The ID of the street to filter by.
   * @return A list of ApartmentEntities associated with the given street ID.
   */
  List<ApartmentEntity> findByHouseStreetId(Long streetId);
}

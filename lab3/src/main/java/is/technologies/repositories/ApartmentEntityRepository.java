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
}

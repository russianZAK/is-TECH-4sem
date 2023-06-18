package is.technologies.services;

import is.technologies.entities.ApartmentEntity;
import is.technologies.repositories.ApartmentEntityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 Service class for {@link ApartmentEntity}.
 */
@Service
public class ApartmentEntityService {

  private ApartmentEntityRepository repository;

  /**
   Constructs an {@link ApartmentEntityService} with the given repository.
   @param repository the {@link ApartmentEntityRepository} to use.
   */
  @Autowired
  public ApartmentEntityService(ApartmentEntityRepository repository) {
    this.repository = repository;
  }

  /**
   Updates an {@link ApartmentEntity} with the given ID.
   @param id the ID of the {@link ApartmentEntity} to update.
   @param entity the updated {@link ApartmentEntity} object.
   @return the updated {@link ApartmentEntity} object.
   */
  public ApartmentEntity update(Long id, ApartmentEntity entity) {
    ApartmentEntity existingEntity = repository.findById(id).orElse(null);
    if (existingEntity != null) {
      existingEntity = entity;
    }
    return repository.save(existingEntity);
  }

  /**
   Returns a list of {@link ApartmentEntity} objects with the given house ID.
   @param id the ID of the house to search for.
   @return a list of {@link ApartmentEntity} objects with the given house ID.
   */
  public List<ApartmentEntity> findByHouseId(Long id) {
    return repository.findByHouseId(id);
  }

  /**
   Returns an {@link ApartmentEntity} with the given ID.
   @param id the ID of the {@link ApartmentEntity} to retrieve.
   @return the {@link ApartmentEntity} with the given ID.
   */
  public ApartmentEntity getById(Long id) {
    return repository.findById(id).get();
  }

  /**
   Saves an {@link ApartmentEntity}.
   @param entity the {@link ApartmentEntity} to save.
   @return the saved {@link ApartmentEntity}.
   */
  public ApartmentEntity save(ApartmentEntity entity) {
    return repository.save(entity);
  }

  /**
   Returns a list of all {@link ApartmentEntity} objects.
   @return a list of all {@link ApartmentEntity} objects.
   */
  public List<ApartmentEntity> getAll() {
    return repository.findAll();
  }

  /**
   Deletes an {@link ApartmentEntity} with the given ID.
   @param id the ID of the {@link ApartmentEntity} to delete.
   */
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  /**
   Deletes an {@link ApartmentEntity}.
   @param entity the {@link ApartmentEntity} to delete.
   */
  public void delete(ApartmentEntity entity) {
    repository.delete(entity);
  }

  /**
   Deletes all {@link ApartmentEntity} objects.
   */
  public void deleteAll() {
    repository.deleteAll();
  }

  /**
   * Retrieves a list of ApartmentEntities by the ID of their associated HouseEntity's street.
   * @param streetId The ID of the street to filter by.
   * @return A list of ApartmentEntities associated with the given street ID.
   */
  public List<ApartmentEntity> findByHouseStreetId(@Param("streetId") Long streetId) {
    return repository.findByHouseStreetId(streetId);
  }
}

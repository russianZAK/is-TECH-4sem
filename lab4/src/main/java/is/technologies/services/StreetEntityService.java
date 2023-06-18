package is.technologies.services;

import is.technologies.entities.StreetEntity;
import is.technologies.repositories.StreetEntityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 This class is a service layer for StreetEntity, providing methods for CRUD operations and business logic.
 */
@Service
public class StreetEntityService {

  private StreetEntityRepository repository;

  /**
   Constructor for creating a new StreetEntityService object.
   @param repository The StreetEntityRepository to be used for data access.
   */
  @Autowired
  public StreetEntityService(StreetEntityRepository repository) {
    this.repository = repository;
  }

  /**
   Returns the StreetEntity corresponding to the given house ID.
   @param houseId The ID of the house to search for.
   @return The StreetEntity corresponding to the given house ID.
   */
  public StreetEntity findByHouseId(Long houseId){
    return repository.findByHouseId(houseId);
  }

  /**
   Returns the StreetEntity corresponding to the given ID.
   @param id The ID of the StreetEntity to retrieve.
   @return The StreetEntity corresponding to the given ID.
   */
  public StreetEntity getById(Long id) {
    return repository.findById(id).get();
  }

  /**
   Updates the StreetEntity with the given ID to match the provided entity.
   @param id The ID of the StreetEntity to update.
   @param entity The updated StreetEntity data.
   @return The updated StreetEntity.
   */
  public StreetEntity update(Long id, StreetEntity entity) {
    StreetEntity existingEntity = repository.findById(id).orElse(null);
    if (existingEntity != null) {
      existingEntity = entity;
    }
    return repository.save(existingEntity);
  }

  /**
   Saves the given StreetEntity to the database.
   @param entity The StreetEntity to save.
   @return The saved StreetEntity.
   */
  public StreetEntity save(StreetEntity entity) {
    return repository.save(entity);
  }

  /**
   Returns a list of all StreetEntity objects in the database.
   @return A list of all StreetEntity objects in the database.
   */
  public List<StreetEntity> getAll() {
    return repository.findAll();
  }

  /**
   Deletes the StreetEntity corresponding to the given ID from the database.
   @param id The ID of the StreetEntity to delete.
   */
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  /**
   Deletes the given StreetEntity from the database.
   @param entity The StreetEntity to delete.
   */
  public void delete(StreetEntity entity) {
    repository.delete(entity);
  }

  /**
   Deletes all StreetEntity objects from the database.
   */
  public void deleteAll() {
    repository.deleteAll();
  }
}

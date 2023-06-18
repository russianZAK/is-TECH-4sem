package is.technologies.services;


import is.technologies.entities.ApartmentEntity;
import is.technologies.entities.HouseEntity;
import is.technologies.entities.StreetEntity;
import is.technologies.repositories.HouseEntityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 Service class for HouseEntity.
 */
@Service
public class HouseEntityService {

  private HouseEntityRepository repository;

  /**
   Constructor to inject the HouseEntityRepository.
   @param repository the repository to use
   */
  @Autowired
  public HouseEntityService(HouseEntityRepository repository) {
    this.repository = repository;
  }

  /**
   Finds all houses by the given street ID.
   @param id the ID of the street
   @return a list of houses on the given street
   */
  public List<HouseEntity> findByStreetId(Long id){
    return repository.findByStreetId(id);
  }

  /**
   Finds the house with the given ID.
   @param id the ID of the house
   @return the house with the given ID
   */
  public HouseEntity getById(Long id) {
    return repository.findById(id).get();
  }

  /**
   Updates the house with the given ID to match the provided entity.
   @param id the ID of the house to update
   @param entity the entity containing the updated house information
   @return the updated house entity
   */
  public HouseEntity update(Long id, HouseEntity entity) {
    HouseEntity existingEntity = repository.findById(id).orElse(null);
    if (existingEntity != null) {
      existingEntity = entity;
    }
    return repository.save(existingEntity);
  }

  /**
   Finds the house that contains the given apartment ID.
   @param apartmentId the ID of the apartment
   @return the house that contains the given apartment
   */
  public HouseEntity findByApartmentId(Long apartmentId){
    return repository.findByApartmentId(apartmentId);
  }

  /**
   Saves the provided house entity.
   @param entity the house entity to save
   @return the saved house entity
   */
  public HouseEntity save(HouseEntity entity) {
    return repository.save(entity);
  }

  /**
   Returns a list of all house entities.
   @return a list of all house entities
   */
  public List<HouseEntity> getAll() {
    return repository.findAll();
  }

  /**
   Deletes the house with the given ID.
   @param id the ID of the house to delete
   */
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  /**
   Deletes the provided house entity.
   @param entity the house entity to delete
   */
  public void delete(HouseEntity entity) {
    repository.delete(entity);
  }

  /**
   Deletes all house entities.
   */
  public void deleteAll() {
    repository.deleteAll();
  }
}

package is.technologies.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import is.technologies.entities.ApartmentEntity;
import is.technologies.entities.MyUserPrincipal;
import is.technologies.entities.User;
import is.technologies.services.ApartmentEntityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 The ApartmentEntityController class represents a RESTful API controller that handles requests related to
 apartment entities. It is responsible for creating, updating, deleting and retrieving apartment entities.
 This controller uses the ApartmentEntityService class to perform business logic and data access operations.
 The API documentation for this controller is generated using Swagger. The @Api annotation is used to define
 the API value and tags.
 */
@Controller
@RequestMapping("/apartment-entity")
@Api(value = "ApartmentEntityController", tags = {"ApartmentEntityController"})
public class ApartmentEntityController {

  @Autowired
  private ApartmentEntityService service;

  /**
   Creates a new apartment entity by calling the save() method of the ApartmentEntityService class.
   @param apartment - The apartment entity to be created
   @return ResponseEntity<ApartmentEntity> - A response entity with the created apartment entity and HTTP status 200 OK
   */
  @PostMapping("/save")
  @ApiOperation(value = "Create a new apartment entity")
  public ResponseEntity<ApartmentEntity> save(@RequestBody ApartmentEntity apartment) {
    service.save(apartment);
    return ResponseEntity.ok(apartment);
  }

  /**
   Updates an existing apartment entity by calling the update() method of the ApartmentEntityService class.
   @param id - The ID of the apartment entity to be updated
   @param apartment - The updated apartment entity
   @return ResponseEntity<ApartmentEntity> - A response entity with the updated apartment entity and HTTP status 200 OK
   */
  @PutMapping("/update/{id}")
  @ApiOperation(value = "Update an existing apartment entity")
  public ResponseEntity<ApartmentEntity> update(@PathVariable Long id, @RequestBody ApartmentEntity apartment) {
    service.update(id, apartment);
    return ResponseEntity.ok(apartment);
  }

  /**
   Deletes an existing apartment entity by calling the deleteById() method of the ApartmentEntityService class.
   @param id - The ID of the apartment entity to be deleted
   @return ResponseEntity<Void> - A response entity with HTTP status 204 NO_CONTENT
   */
  @DeleteMapping("/delete/{id}")
  @ApiOperation(value = "Delete a apartment entity by ID")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   Retrieves an existing apartment entity by calling the getById() method of the ApartmentEntityService class.
   @param id - The ID of the apartment entity to be retrieved
   @return ResponseEntity<ApartmentEntity> - A response entity with the retrieved apartment entity and HTTP status 200 OK
   */
  @GetMapping("/get/{id}")
  @ApiOperation(value = "Get an apartment entity by ID")
  public ResponseEntity<ApartmentEntity> getById(@PathVariable Long id) {
    ApartmentEntity apartment = service.getById(id);
    return ResponseEntity.ok(apartment);
  }

  /**
   * Retrieves all apartment entities.
   * @return A ResponseEntity containing a list of ApartmentEntity objects.
   */
  @GetMapping("/get-all")
  @ApiOperation(value = "Get all apartment entities")
  public ResponseEntity<List<ApartmentEntity>> getAll() {
    MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = myUserPrincipal.getUser();

    if (user.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
      return ResponseEntity.ok(service.getAll());
    }

    List<ApartmentEntity> apartments = service.findByHouseStreetId(user.getStreetId());
    return ResponseEntity.ok(apartments);
  }
}

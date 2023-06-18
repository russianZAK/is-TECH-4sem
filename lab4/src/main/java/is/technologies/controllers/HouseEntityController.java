package is.technologies.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import is.technologies.entities.HouseEntity;
import is.technologies.entities.MyUserPrincipal;
import is.technologies.entities.User;
import is.technologies.services.HouseEntityService;
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

 The HouseEntityController class represents a RESTful API controller that handles requests related to
 house entities. It is responsible for creating, updating, deleting and retrieving house entities.
 This controller uses the HouseEntityService class to perform business logic and data access operations.
 The API documentation for this controller is generated using Swagger. The @Api annotation is used to define
 the API value and tags.
 @author [Your Name]
 @version 1.0
 @since [date]
 */
@Controller
@RequestMapping("/house-entity")
@Api(value = "HouseEntityController", tags = {"HouseEntityController"})
public class HouseEntityController {

  @Autowired
  private HouseEntityService service;

  /**
   Creates a new house entity by calling the save() method of the HouseEntityService class.
   @param house - The house entity to be created
   @return ResponseEntity<HouseEntity> - A response entity with the created house entity and HTTP status 200 OK
   */
  @PostMapping("/save")
  @ApiOperation(value = "Create a new house entity")
  public ResponseEntity<HouseEntity> save(@RequestBody HouseEntity house) {
    service.save(house);
    return ResponseEntity.ok(house);
  }

  /**
   Updates an existing house entity by calling the update() method of the HouseEntityService class.
   @param id - The ID of the house entity to be updated
   @param house - The updated house entity
   @return ResponseEntity<HouseEntity> - A response entity with the updated house entity and HTTP status 200 OK
   */
  @PutMapping("/update/{id}")
  @ApiOperation(value = "Update an existing house entity")
  public ResponseEntity<HouseEntity> update(@PathVariable Long id, @RequestBody HouseEntity house) {
    service.update(id, house);
    return ResponseEntity.ok(house);
  }

  /**
   Deletes an existing house entity by calling the deleteById() method of the HouseEntityService class.
   @param id - The ID of the house entity to be deleted
   @return ResponseEntity<Void> - A response entity with HTTP status 204 NO_CONTENT
   */
  @DeleteMapping("/delete/{id}")
  @ApiOperation(value = "Delete a house entity by ID")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   Retrieves an existing house entity by calling the getById() method of the HouseEntityService class.
   @param id - The ID of the house entity to be retrieved
   @return ResponseEntity<HouseEntity> - A response entity with the retrieved house entity and HTTP status 200 OK
   */
  @GetMapping("/get/{id}")
  @ApiOperation(value = "Get a house entity by ID")
  public ResponseEntity<HouseEntity> getById(@PathVariable Long id) {
    HouseEntity house = service.getById(id);
    return ResponseEntity.ok(house);
  }

  /**
   * Retrieves all house entities.
   * @return A ResponseEntity containing a list of HouseEntity objects.
   */
  @GetMapping("/get-all")
  @ApiOperation(value = "Get a house entities")
  public ResponseEntity<List<HouseEntity>> getAll() {
    MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = myUserPrincipal.getUser();
    if (user.getRole().equalsIgnoreCase("ROLE_ADMIN")){
      List<HouseEntity> houses = service.getAll();
      return ResponseEntity.ok(houses);
    }
    List<HouseEntity> houses = service.findByStreetId(user.getStreetId());
    return ResponseEntity.ok(houses);
  }
}

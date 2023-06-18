package is.technologies.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import is.technologies.entities.MyUserPrincipal;
import is.technologies.entities.StreetEntity;
import is.technologies.entities.User;
import is.technologies.services.StreetEntityService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 Controller class for managing {@link StreetEntity} objects.
 */
@Controller
@RequestMapping("/street-entity")
@Api(value = "StreetEntityController", tags = {"StreetEntityController"})
public class StreetEntityController {

  @Autowired
  private StreetEntityService service;

  /**
   Creates a new street entity.
   @param street the street entity to be created.
   @return the created street entity.
   */
  @PostMapping("/save")
  @ApiOperation(value = "Create a new street entity")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<StreetEntity> save(@RequestBody StreetEntity street) {
    service.save(street);
    return ResponseEntity.ok(street);
  }

  /**
   Updates an existing street entity.
   @param id the ID of the street entity to be updated.
   @param street the updated street entity.
   @return the updated street entity.
   */
  @PutMapping("/update/{id}")
  @ApiOperation(value = "Update an existing street entity")
  public ResponseEntity<StreetEntity> update(@PathVariable Long id, @RequestBody StreetEntity street) {
    service.update(id, street);
    return ResponseEntity.ok(street);
  }

  /**
   Deletes a street entity by its ID.
   @param id the ID of the street entity to be deleted.
   @return a response entity with no content status.
   */
  @DeleteMapping("/delete/{id}")
  @ApiOperation(value = "Delete a street entity by ID")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   Retrieves a street entity by its ID.
   @param id the ID of the street entity to be retrieved.
   @return the retrieved street entity.
   */
  @GetMapping("/get/{id}")
  @ApiOperation(value = "Get a street entity by ID")
  public ResponseEntity<StreetEntity> getById(@PathVariable Long id) {
    StreetEntity street = service.getById(id);
    return ResponseEntity.ok(street);
  }

  /**
   * Retrieves all street entities.
   * @return A ResponseEntity containing a list of StreetEntity objects.
   */
  @GetMapping("/get-all")
  @ApiOperation(value = "Get a street entities")
  public ResponseEntity<List<StreetEntity>> getAll() {
    MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = myUserPrincipal.getUser();
    if (user.getRole().equalsIgnoreCase("ROLE_ADMIN")){
      return ResponseEntity.ok(service.getAll());
    }
    StreetEntity street = service.getById(user.getStreetId());
    return ResponseEntity.ok(Collections.singletonList(street));
  }
}

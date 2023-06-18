package is.technologies.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import is.technologies.entities.User;
import is.technologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing user operations.
 */
@Controller
@RequestMapping("/user")
@Api(value = "UserController", tags = {"UserController"})
public class UserController {

  @Autowired
  private UserService service;

  /**
   * Create a new user.
   * @param user The user object to be saved.
   * @return ResponseEntity with the saved user.
   */
  @PostMapping("/save")
  @ApiOperation(value = "Create a new user")
  public ResponseEntity<User> save(@RequestBody User user) {
    service.save(user);
    return ResponseEntity.ok(user);
  }

  /**
   * Update an existing user.
   * @param id   The ID of the user to be updated.
   * @param user The updated user object.
   * @return ResponseEntity with the updated user.
   */
  @PutMapping("/update/{id}")
  @ApiOperation(value = "Update an existing user")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
    service.update(id, user);
    return ResponseEntity.ok(user);
  }

  /**
   * Delete a user by ID.
   * @param id The ID of the user to be deleted.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping("/delete/{id}")
  @ApiOperation(value = "Delete a user by ID")
  public ResponseEntity<Void> deleteById(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Get a user by ID.
   * @param id The ID of the user to retrieve.
   * @return ResponseEntity with the retrieved user.
   */
  @GetMapping("/get/{id}")
  @ApiOperation(value = "Get a user by ID")
  public ResponseEntity<User> getById(@PathVariable Long id) {
    User user = service.getById(id);
    return ResponseEntity.ok(user);
  }
}

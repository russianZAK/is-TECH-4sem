package is.technologies.services;

import is.technologies.entities.MyUserPrincipal;
import is.technologies.entities.User;
import is.technologies.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations and integrating with the user repository.
 */
@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository repository;

  /**
   * Constructs a new UserService instance with the provided UserRepository.
   * @param repository The UserRepository used for database operations.
   */
  public UserService(UserRepository repository){
    this.repository = repository;
  }

  /**
   * Loads a user by the given username.
   * @param username The username of the user to load.
   * @return The UserDetails representing the loaded user.
   * @throws UsernameNotFoundException if the user with the specified username is not found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new MyUserPrincipal(user);
  }

  /**
   * Retrieves a user by their ID.
   * @param id The ID of the user to retrieve.
   * @return The User object representing the retrieved user.
   */
  public User getById(Long id) {
    return repository.findById(id).get();
  }

  /**
   * Updates an existing user.
   * @param id     The ID of the user to update.
   * @param entity The updated User object.
   * @return The User object representing the updated user.
   */
  public User update(Long id, User entity) {
    User existingEntity = repository.findById(id).orElse(null);
    if (existingEntity != null) {
      existingEntity = entity;
    }
    return repository.save(existingEntity);
  }

  /**
   * Finds a user by their username.
   * @param username The username of the user to find.
   * @return The User object representing the found user, or null if not found.
   */
  public User findByUsername(String username){
    return repository.findByUsername(username);
  }

  /**
   * Saves a new user.
   * @param entity The User object to be saved.
   * @return The User object representing the saved user.
   */
  public User save(User entity) {
    return repository.save(entity);
  }

  /**
   * Retrieves all users.
   * @return A list of User objects representing all users.
   */
  public List<User> getAll() {
    return repository.findAll();
  }

  /**
   * Deletes a user by their ID.
   * @param id The ID of the user to delete.
   */
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  /**
   * Deletes a user.
   * @param entity The User object to be deleted.
   */
  public void delete(User entity) {
    repository.delete(entity);
  }

  /**
   * Deletes all users.
   */
  public void deleteAll() {
    repository.deleteAll();
  }
}
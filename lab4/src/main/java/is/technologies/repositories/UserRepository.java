package is.technologies.repositories;

import is.technologies.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find a user by username.
   * @param username The username of the user to find.
   * @return The User object representing the found user, or null if not found.
   */
  public User findByUsername(String username);
}
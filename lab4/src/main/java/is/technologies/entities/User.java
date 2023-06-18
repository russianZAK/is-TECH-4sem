package is.technologies.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 Represents a user in the database.
 */
@Entity
@Table(name = "users", schema = "public")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Basic
  @Column(name = "username", nullable = false, length = 255)
  private String username;

  @Basic
  @Column(name = "password", nullable = false, length = 255)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private String password;

  @Basic
  @Enumerated(EnumType.STRING)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @Column(name = "role", nullable = false)
  private Role role;

  @Column(name = "accountnonexpired", nullable = false)
  private boolean accountNonExpired;

  @Column(name = "accountnonlocked", nullable = false)
  private boolean accountNonLocked;

  @Column(name = "credentialsnonexpired", nullable = false)
  private boolean credentialsNonExpired;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @Column(name = "street_id")
  private Long streetId;

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    this.password = bcryptPasswordEncoder.encode(password);
  }

  public String getRole() {
    return role.getValue();
  }

  public void setRole(String value) {
    this.role = Role.fromValue(value);
  }

  public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String value;

    Role(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static User.Role fromValue(String value) {
      for (User.Role role : User.Role.values()) {
        if (role.getValue().equalsIgnoreCase(value)) {
          return role;
        }
      }
      throw new IllegalArgumentException("Invalid building type value: " + value);
    }
  }
}
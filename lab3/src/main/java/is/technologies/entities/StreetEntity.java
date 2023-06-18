package is.technologies.entities;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 Represents a street entity in the database.
 */
@Entity
@Table(name = "street", schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StreetEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic
  @Column(name = "name", nullable = false, length = 255)
  private String name;
  @Basic
  @Column(name = "postal_code", nullable = false)
  private Long postalCode;
}

package is.technologies.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 Represents a apartment entity in the database.
 */
@Entity
@Table(name = "apartment", schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic
  @Column(name = "number", nullable = false)
  private int number;
  @Basic
  @Column(name = "square", nullable = false)
  private int square;
  @Basic
  @Column(name = "num_rooms", nullable = false)
  private int numRooms;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "house_id")
  private HouseEntity house;
}

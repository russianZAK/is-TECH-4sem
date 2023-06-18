package is.technologies.entities;

import java.sql.Date;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 Represents a house entity in the database.
 */
@Entity
@Table(name = "house", schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private Long id;
  @Basic
  @Column(name = "name", nullable = false, length = 255)
  private String name;
  @Basic
  @Column(name = "build_date", nullable = false)
  private Date buildDate;
  @Basic
  @Column(name = "num_floors", nullable = false)
  private int numFloors;
  @Basic
  @Enumerated(EnumType.STRING)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  @Column(name = "type", nullable = false)
  private TypeOfBuilding type;
  @Basic
  @Column(name = "material", length = 255)
  private String material;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "street_id")
  private StreetEntity street;

  public String getType() {
    return type.getValue();
  }

  public void setType(String value) {
    this.type = TypeOfBuilding.fromValue(value);
  }

  public enum TypeOfBuilding {
    RESIDENTIAL("Residential"),
    COMMERCIAL("Commercial"),
    GARAGE("Garage"),
    UTILITY("Utility");

    private final String value;

    TypeOfBuilding(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static TypeOfBuilding fromValue(String value) {
      for (TypeOfBuilding type : TypeOfBuilding.values()) {
        if (type.getValue().equalsIgnoreCase(value)) {
          return type;
        }
      }
      throw new IllegalArgumentException("Invalid building type value: " + value);
    }
  }
}

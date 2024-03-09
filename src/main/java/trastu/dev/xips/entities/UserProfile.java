package trastu.dev.xips.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private String cityName;
    private String zipCode;
    private String country;
    @OneToOne
    @JoinColumn(name="user_id", unique = true)
    private User user;

}

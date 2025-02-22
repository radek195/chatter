package pl.radek.chatter.infrastructure.repository.user.preference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.radek.chatter.domain.model.user.preference.UserPreference;

@Entity
@Table(name = "user_preferences")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserPreferenceEntity {

    @Id
    @SequenceGenerator(
            name = "user_preference_sequence",
            sequenceName = "user_preference_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_preference_sequence")
    private Long id;

    @Column(name = "min_age")
    private Integer minAge;
    @Column(name = "max_age")
    private Integer maxAge;

    public static UserPreferenceEntity from(UserPreference userPreference) {
        return UserPreferenceEntity.builder()
                .minAge(userPreference.getMinAge())
                .maxAge(userPreference.getMaxAge())
                .build();
    }
}

package pl.radek.chatter.infrastructure.repository.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.radek.chatter.domain.model.user.Gender;
import pl.radek.chatter.infrastructure.repository.user.preference.UserPreferenceEntity;

@Entity
@Table(name = "users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_preference_id", referencedColumnName = "id")
    private UserPreferenceEntity userPreference;

}


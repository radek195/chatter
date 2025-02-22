package pl.radek.chatter.interfaces.controller.user;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import pl.radek.chatter.domain.model.user.preference.UserPreference;

@Getter
public class UserPreferenceRequest {
    @Min(value = 16, message = "Minimum age cannot be lower than 16.")
    private Integer minAge;
    private Integer maxAge;

    public UserPreference toDto() {
        return UserPreference.builder()
                .minAge(minAge)
                .maxAge(maxAge)
                .build();
    }
}

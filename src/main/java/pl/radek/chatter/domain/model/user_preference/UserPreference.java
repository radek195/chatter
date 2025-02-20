package pl.radek.chatter.domain.model.user_preference;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserPreference {
    private Integer minAge;
    private Integer maxAge;
}

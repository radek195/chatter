package pl.radek.chatter.domain.service.user.matching.matchers;

import org.springframework.stereotype.Component;
import pl.radek.chatter.domain.service.user.matching.PreferenceMatcher;
import pl.radek.chatter.infrastructure.repository.user.UserEntity;

@Component
public class MaxAgeMatcher implements PreferenceMatcher {

    @Override
    public boolean isMatching(UserEntity user, UserEntity candidate) {
        Integer userPreference = user.getUserPreference().getMaxAge();
        Integer candidatePreference = candidate.getUserPreference().getMaxAge();

        boolean matchesUser = userPreference == null || candidate.getAge() <= userPreference;
        boolean matchesCandidate = candidatePreference == null || user.getAge() <= candidatePreference;

        return matchesUser && matchesCandidate;
    }
}

package pl.radek.chatter.domain.service.user.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.infrastructure.repository.user.UserEntity;

import java.util.List;

public interface UserMatchingService {

    List<UserEntity> findMatchingUsersByPreference(UserEntity user, List<UserEntity> candidates);

    @Service
    @RequiredArgsConstructor
    class Impl implements UserMatchingService {

        private final List<PreferenceMatcher> preferenceMatchers;

        @Override
        public List<UserEntity> findMatchingUsersByPreference(UserEntity user, List<UserEntity> candidates) {
            return candidates.stream()
                    .filter(candidate -> isMatching(user, candidate))
                    .toList();
        }

        private boolean isMatching(UserEntity user, UserEntity candidate) {
            return preferenceMatchers.stream()
                    .allMatch(matcher -> matcher.isMatching(user, candidate));
        }
    }

}
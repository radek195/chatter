package pl.radek.chatter.domain.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.model.user.User;
import pl.radek.chatter.domain.model.user.preference.UserPreference;
import pl.radek.chatter.infrastructure.exceptions.UserNotFoundException;
import pl.radek.chatter.infrastructure.repository.user.UserEntity;
import pl.radek.chatter.infrastructure.repository.user.UserRepository;
import pl.radek.chatter.infrastructure.repository.user.preference.UserPreferenceEntity;

public interface UserService {
    Long createGuestUser(User user);

    void addUserPreferenceForUser(UserPreference userPreferencesDto, Long id);

    @Service
    @RequiredArgsConstructor
    class Impl implements UserService {

        private final UserRepository userRepository;

        @Override
        public Long createGuestUser(User user) {
            return userRepository.save(user.toEntity()).getId();
        }

        @Override
        public void addUserPreferenceForUser(UserPreference userPreferenceDto, Long id) {
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + id));
            UserPreferenceEntity userPreferenceEntity = userEntity.getUserPreference();

            if (userPreferenceEntity == null) {
                userPreferenceEntity = UserPreferenceEntity.from(userPreferenceDto);
                userEntity.setUserPreference(userPreferenceEntity);
            } else {
                userPreferenceEntity.setMinAge(userPreferenceDto.getMinAge());
                userPreferenceEntity.setMaxAge(userPreferenceDto.getMaxAge());
            }

            userRepository.save(userEntity);
        }
    }
}

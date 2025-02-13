package pl.radek.chatter.domain.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.infrastructure.repository.SubscriberRepository;

public interface UserService {
    Long createGuestUser(User user);

    @Service
    @RequiredArgsConstructor
    class Impl implements UserService {

        private final SubscriberRepository subscriberRepository;

        @Override
        public Long createGuestUser(User user) {
            return subscriberRepository.save(user.toEntity()).getId();
        }
    }
}

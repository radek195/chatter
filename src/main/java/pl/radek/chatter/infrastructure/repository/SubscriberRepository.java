package pl.radek.chatter.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<UserEntity, Long> {
}

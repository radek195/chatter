package pl.radek.chatter.infrastructure.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = """
                    SELECT * FROM chatter.users
                        WHERE chatroom_id IN (
                            select chatroom_id from chatter.users group by chatroom_id having count(*) = 1
                        ) AND id != ?1
                    """,
            nativeQuery = true)
    List<UserEntity> findAllWithUniqueChatroomIdExcluding(Long userId);

    List<UserEntity> findAllByChatroomId(UUID chatroomId);
}

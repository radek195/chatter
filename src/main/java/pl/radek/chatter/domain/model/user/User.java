package pl.radek.chatter.domain.model.user;

import lombok.Builder;
import lombok.Data;
import pl.radek.chatter.infrastructure.repository.user.UserEntity;

import java.util.UUID;

@Data
@Builder
public class User {
    private Long id;
    private String nickname;
    private Gender gender;
    private int age;
    private UUID chatroomId;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .nickname(this.getNickname())
                .gender(this.getGender())
                .age(this.getAge())
                .chatroomId(this.getChatroomId())
                .build();
    }
}

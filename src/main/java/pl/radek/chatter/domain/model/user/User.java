package pl.radek.chatter.domain.model.user;

import lombok.Builder;
import lombok.Data;
import pl.radek.chatter.infrastructure.repository.user.UserEntity;

@Data
@Builder
public class User {
    private Long id;
    private String nickname;
    private Gender gender;
    private int age;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .nickname(this.getNickname())
                .gender(this.getGender())
                .age(this.getAge())
                .build();
    }
}

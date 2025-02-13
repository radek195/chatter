package pl.radek.chatter.domain.service.user;

import lombok.Builder;
import lombok.Data;
import pl.radek.chatter.infrastructure.repository.Gender;
import pl.radek.chatter.infrastructure.repository.UserEntity;

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

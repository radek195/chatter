package pl.radek.chatter.interfaces.controller.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import pl.radek.chatter.domain.service.user.User;
import pl.radek.chatter.infrastructure.repository.Gender;

@Getter
public class GuestRequest {

    @NotNull
    @Size(min = 3, max = 18,
            message = "Nickname must be between 3 and 18 characters.")
    private String nickname;
    private Gender gender;

    @NotNull
    @Min(value = 16, message = "You must be 16 or older.")
    private int age;

    public User toUserDto() {
        return User.builder()
                .nickname(this.getNickname())
                .gender(this.getGender())
                .age(this.getAge())
                .build();
    }
}

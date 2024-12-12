package pl.radek.chatter.interfaces.controller.chatmember;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/")
public class ChatMemberController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterBody body) {
        UUID memberUuid = UUID.randomUUID();
        return RegisterResponse.builder()
                .uuid(memberUuid)
                .build();
    }
}

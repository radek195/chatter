package pl.radek.chatter.interfaces.controller.chatmember;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.radek.chatter.domain.ChatMemberService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ChatMemberController {

    private final ChatMemberService chatMemberService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterBody body) {
        UUID memberUuid = chatMemberService.saveNewChatMember(body.getNickname());
        System.out.println(memberUuid.toString());
        return RegisterResponse.builder()
                .uuid(memberUuid)
                .build();
    }
}

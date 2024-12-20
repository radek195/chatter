package pl.radek.chatter.interfaces.controller.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.radek.chatter.domain.ChatroomService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ChatRoomController {

    private final ChatroomService chatroomService;

    @GetMapping("/chatroom")
    @ResponseStatus(HttpStatus.OK)
    public ChatroomResponse getChatroom() {
        UUID chatroomId = chatroomService.getChatroom();
        return ChatroomResponse.builder()
                .uuid(chatroomId)
                .build();
    }
}

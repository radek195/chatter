package pl.radek.chatter.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/register")
    public UUID register(@RequestBody RegisterBody body) {
        //TODO determine chatroom

        return UUID.fromString("25c2caca-dc51-4e0c-ac93-90aefcefe37b"); //todo return determined chatroom uuid
    }

    @MessageMapping("/message/{room}")
    public void sendMessage(@DestinationVariable String room, @Payload MessagePayload message) {
        messagingTemplate.convertAndSend("/topic/message/room/" + room, message);
    }

}

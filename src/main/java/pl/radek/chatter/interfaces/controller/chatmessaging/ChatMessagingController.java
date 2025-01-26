package pl.radek.chatter.interfaces.controller.chatmessaging;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pl.radek.chatter.domain.message.Message;

@Controller
@RequiredArgsConstructor
public class ChatMessagingController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message/{room}")
    public void sendMessage(@DestinationVariable String room, @Payload Message message) {
        messagingTemplate.convertAndSend("/topic/message/room/" + room, message);
    }

}

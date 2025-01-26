package pl.radek.chatter.interfaces.controller.chatmessaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import pl.radek.chatter.domain.systemmessager.SystemMessagingService;

@Component
@RequiredArgsConstructor
public class WebSocketListener {

    private final SystemMessagingService systemMessagingService;

    @EventListener
    public void handleWebSocketConnectedListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) throws JsonProcessingException {
        System.out.println("Received a new web socket subscription");
        systemMessagingService.handleNewSubscription(event);
    }
}

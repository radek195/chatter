package pl.radek.chatter.domain.systemmessager;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import pl.radek.chatter.domain.message.Message;

public interface SystemMessagingService {

    void handleNewSubscription(SessionSubscribeEvent event) throws JsonProcessingException;

    @Service
    @RequiredArgsConstructor
    class Impl implements SystemMessagingService {

        private final SimpMessagingTemplate messagingTemplate;
        private final ObjectMapper mapper;

        @Override
        public void handleNewSubscription(SessionSubscribeEvent event) throws JsonProcessingException {
            String destination = getDestination(event);
            String nickname = getNickname(event);
            String messageContent = nickname + " has joined the room.";
            Message systemMessage = Message.getSystemMessage(messageContent);

            messagingTemplate.convertAndSend(destination, mapper.writeValueAsString(systemMessage));
        }

        private String getStompHeaderFromEvent(SessionSubscribeEvent event, String header) {
            StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

            return accessor.getNativeHeader(header).get(0);
        }

        private String getDestination(SessionSubscribeEvent event) {
            return getStompHeaderFromEvent(event, "destination");
        }

        private String getNickname(SessionSubscribeEvent event) {
            return getStompHeaderFromEvent(event, "nickname");
        }

    }
}

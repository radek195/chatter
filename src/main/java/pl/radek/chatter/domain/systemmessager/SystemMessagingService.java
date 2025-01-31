package pl.radek.chatter.domain.systemmessager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.message.Message;
import pl.radek.chatter.domain.message.Type;

public interface SystemMessagingService {

    void sendMessage(String destination, String messageContent);

    @Service
    @RequiredArgsConstructor
    class Impl implements SystemMessagingService {

        private final SimpMessagingTemplate messagingTemplate;
        private final ObjectMapper mapper;

        @Override
        public void sendMessage(String destination, String messageContent) {
            messagingTemplate.convertAndSend(destination, getSystemMessage(messageContent));
        }

        private String getSystemMessage(String messageContent) {
            Message systemMessage = Message.builder()
                    .type(Type.SYSTEM)
                    .content(messageContent)
                    .build();
            try {
                return mapper.writeValueAsString(systemMessage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Could not map object " + systemMessage);
            }
        }
    }
}

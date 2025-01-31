package pl.radek.chatter.domain.service.chatroom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Configuration
public class ChatroomConfig {
    @Bean
    public Queue<UUID> getChatroomQueueBean() {
        return new LinkedList<>();
    }
}

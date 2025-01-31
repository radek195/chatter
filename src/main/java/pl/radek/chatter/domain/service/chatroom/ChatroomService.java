package pl.radek.chatter.domain.service.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.UUID;

public interface ChatroomService {

    UUID getChatroom();

    @Service
    @RequiredArgsConstructor
    class Impl implements ChatroomService {

        private final Queue<UUID> chatrooms;

        public UUID getChatroom() {
            UUID chatroomId = chatrooms.poll();
            if (chatroomId == null) {
                UUID newChatroomId = UUID.randomUUID();
                chatrooms.offer(newChatroomId);
                return newChatroomId;
            }
            return chatroomId;
        }
    }
}

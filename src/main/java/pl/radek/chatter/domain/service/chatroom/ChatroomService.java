package pl.radek.chatter.domain.service.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.model.user.User;
import pl.radek.chatter.domain.service.user.matching.UserMatchingService;
import pl.radek.chatter.infrastructure.exceptions.UserNotFoundException;
import pl.radek.chatter.infrastructure.repository.user.UserEntity;
import pl.radek.chatter.infrastructure.repository.user.UserRepository;

import java.util.List;
import java.util.Queue;
import java.util.UUID;

public interface ChatroomService {

    UUID getChatroom();

    UUID getMatchingChatroom(Long userId);

    List<User> getCurrentUsersInRoom(UUID chatroomId);

    @Service
    @RequiredArgsConstructor
    class Impl implements ChatroomService {

        private final UserRepository userRepository;
        private final UserMatchingService userMatchingService;
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

        //TODO fix race condition in that method
        @Override
        public UUID getMatchingChatroom(Long userId) {
            UserEntity requestingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("Could not find user with id: " + userId));

            List<UserEntity> users = userRepository.findAllWithUniqueChatroomIdExcluding(userId);
            List<UserEntity> matchingUserList = userMatchingService.findMatchingUsersByPreference(requestingUser, users);

            UUID chatroomId;
            if (matchingUserList.isEmpty()) {
                chatroomId = UUID.randomUUID();
            } else {
                chatroomId = matchingUserList.get(0).getChatroomId();
            }

            requestingUser.setChatroomId(chatroomId);
            return chatroomId;
        }

        @Override
        public List<User> getCurrentUsersInRoom(UUID chatroomId) {
            return userRepository.findAllByChatroomId(chatroomId).stream()
                    .map(User::from)
                    .toList();
        }
    }
}

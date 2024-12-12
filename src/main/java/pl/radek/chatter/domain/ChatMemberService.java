package pl.radek.chatter.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.pojo.ChatMember;
import pl.radek.chatter.domain.pojo.Status;

import java.util.UUID;

public interface ChatMemberService {

    UUID saveNewChatMember(String nickname);

    @Service
    @RequiredArgsConstructor
    class Impl implements ChatMemberService {

        private final ChatMemberRepository chatMemberRepository;

        @Override
        public UUID saveNewChatMember(String nickname) {
            return chatMemberRepository.save(ChatMember.builder()
                    .id(UUID.randomUUID())
                    .nickname(nickname)
                    .status(Status.BUSY)
                    .build()
            );
        }
    }

}

package pl.radek.chatter.infrastructure;

import org.springframework.stereotype.Repository;
import pl.radek.chatter.domain.pojo.ChatMember;
import pl.radek.chatter.domain.ChatMemberRepository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class ChatMemberRepositoryImpl implements ChatMemberRepository {

    private static HashMap<UUID, ChatMember> currentMembers = new HashMap<>();

    @Override
    public UUID save(ChatMember member) {
        currentMembers.put(member.getId(), member);
        return member.getId();
    }
}

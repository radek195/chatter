package pl.radek.chatter.domain;

import pl.radek.chatter.domain.pojo.ChatMember;

import java.util.UUID;

public interface ChatMemberRepository {
    UUID save(ChatMember member);
}

package pl.radek.chatter.infrastructure.controller;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MessagePayload {
    private Long messageId;
    private String content;
    private String senderNickname;
    private UUID senderId;
}
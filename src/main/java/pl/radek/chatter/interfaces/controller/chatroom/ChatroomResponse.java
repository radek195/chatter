package pl.radek.chatter.interfaces.controller.chatroom;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ChatroomResponse {
    private UUID uuid;
}

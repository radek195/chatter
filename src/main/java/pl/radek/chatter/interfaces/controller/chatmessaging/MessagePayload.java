package pl.radek.chatter.interfaces.controller.chatmessaging;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessagePayload {
    private String content;
    private String senderNickname;
}

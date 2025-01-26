package pl.radek.chatter.domain.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String content;
    private String senderNickname;
    private Type type;

    public static Message getSystemMessage(String content) {
        return Message.builder()
                .type(Type.SYSTEM)
                .content(content)
                .build();
    }
}

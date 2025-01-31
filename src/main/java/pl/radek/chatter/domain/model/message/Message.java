package pl.radek.chatter.domain.model.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String content;
    private String senderNickname;
    private Type type;
}

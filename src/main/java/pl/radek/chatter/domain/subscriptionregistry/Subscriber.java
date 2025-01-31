package pl.radek.chatter.domain.subscriptionregistry;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subscriber {
    private long id;
    private String nickname;
    private String sessionId;
}

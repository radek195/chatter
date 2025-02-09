package pl.radek.chatter.domain.model.subscriber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subscriber {
    private long id;
    private String nickname;
    private String topic;
}

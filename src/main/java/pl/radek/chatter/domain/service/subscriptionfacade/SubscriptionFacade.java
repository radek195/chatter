package pl.radek.chatter.domain.service.subscriptionfacade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.radek.chatter.domain.model.subscriber.Subscriber;
import pl.radek.chatter.domain.service.subscriptionregistry.SubscriptionRegistryService;
import pl.radek.chatter.domain.service.systemmessager.SystemMessagingService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SubscriptionFacade {

    private final SubscriptionRegistryService subscriptionRegistryService;
    private final SystemMessagingService systemMessagingService;

    public void handleSubscription(String topic, Subscriber subscriber) {
        subscriptionRegistryService.addSubscription(topic, subscriber);
        systemMessagingService.sendMessage(topic, subscriber.getNickname() + " has joined the room.");
    }

    public void handleUnsubscription(String sessionId) {
        Map<String, String> nicknameTopicMap = subscriptionRegistryService.removeSubscription(sessionId);
        String topic = nicknameTopicMap.get("topic");
        String message = nicknameTopicMap.get("nickname") + " has left the room.";
        systemMessagingService.sendMessage(topic, message);
    }
}

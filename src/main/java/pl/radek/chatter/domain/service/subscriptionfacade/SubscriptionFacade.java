package pl.radek.chatter.domain.service.subscriptionfacade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.radek.chatter.domain.model.subscriber.Subscriber;
import pl.radek.chatter.domain.service.subscriptionregistry.SubscriptionRegistryService;
import pl.radek.chatter.domain.service.systemmessager.SystemMessagingService;

@Component
@RequiredArgsConstructor
public class SubscriptionFacade {

    private final SubscriptionRegistryService subscriptionRegistryService;
    private final SystemMessagingService systemMessagingService;

    public void handleSubscription(String sessionId, Subscriber subscriber) {
        subscriptionRegistryService.addSubscription(sessionId, subscriber);
        systemMessagingService.sendMessage(subscriber.getTopic(), subscriber.getNickname() + " has joined the room.");
    }

    public void handleUnsubscription(String sessionId) {
        Subscriber removedSubscriber = subscriptionRegistryService.removeSubscription(sessionId);
        String topic = removedSubscriber.getTopic();
        String nickname = removedSubscriber.getNickname();
        systemMessagingService.sendMessage(topic, nickname + " has left the room.");
    }
}

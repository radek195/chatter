package pl.radek.chatter.domain.service.subscriptionregistry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.model.subscriber.Subscriber;

import java.util.concurrent.ConcurrentMap;

public interface SubscriptionRegistryService {
    void addSubscription(String sessionId, Subscriber subscriber);

    Subscriber removeSubscription(String sessionId);

    @Service
    @RequiredArgsConstructor
    class Impl implements SubscriptionRegistryService {

        private final ConcurrentMap<String, Subscriber> sessionToSubscriberMap;

        public void addSubscription(String sessionId, Subscriber subscriber) {
            sessionToSubscriberMap.put(sessionId, subscriber);
        }

        public Subscriber removeSubscription(String sessionId) {
            return sessionToSubscriberMap.remove(sessionId);
        }
    }
}



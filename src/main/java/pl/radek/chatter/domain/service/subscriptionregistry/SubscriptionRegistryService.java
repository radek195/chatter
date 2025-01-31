package pl.radek.chatter.domain.service.subscriptionregistry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.model.subscriber.Subscriber;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface SubscriptionRegistryService {
    void addSubscription(String topic, Subscriber subscriber);

    Subscriber removeSubscription(String sessionId);

    @Service
    @RequiredArgsConstructor
    class Impl implements SubscriptionRegistryService {

        private final ConcurrentMap<String, String> sessionToTopic;
        private final ConcurrentMap<String, Set<Subscriber>> topicToSubscribers;

        public void addSubscription(String topic, Subscriber subscriber) {
            sessionToTopic.put(subscriber.getSessionId(), topic);
            topicToSubscribers.computeIfAbsent(topic, k -> ConcurrentHashMap.newKeySet())
                    .add(subscriber);
        }

        public Subscriber removeSubscription(String sessionId) {
            String topicId = sessionToTopic.remove(sessionId);

            Set<Subscriber> subscribers = topicToSubscribers.get(topicId);
            Subscriber subscriberToRemove = subscribers.stream()
                    .filter(subscriber -> subscriber.getSessionId().equals(sessionId))
                    .findFirst().orElseThrow();

            subscribers.remove(subscriberToRemove);

            if (subscribers.isEmpty()) {
                topicToSubscribers.remove(topicId);
            }
            return subscriberToRemove;
        }
    }
}



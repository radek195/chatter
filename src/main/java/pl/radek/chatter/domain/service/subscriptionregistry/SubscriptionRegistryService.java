package pl.radek.chatter.domain.service.subscriptionregistry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radek.chatter.domain.model.subscriber.Subscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface SubscriptionRegistryService {
    void addSubscription(String topic, Subscriber subscriber);

    Map<String, String> removeSubscription(String sessionId);

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

        public Map<String, String> removeSubscription(String sessionId) {
            String topic = sessionToTopic.remove(sessionId);

            Set<Subscriber> subscribers = topicToSubscribers.get(topic);
            Subscriber subscriberToRemove = subscribers.stream()
                    .filter(subscriber -> subscriber.getSessionId().equals(sessionId))
                    .findFirst().orElseThrow();

            subscribers.remove(subscriberToRemove);

            if (subscribers.isEmpty()) {
                topicToSubscribers.remove(topic);
            }
            Map<String, String> result = new HashMap<>();
            result.put("nickname", subscriberToRemove.getNickname());
            result.put("topic", topic);
            return result;
        }
    }
}



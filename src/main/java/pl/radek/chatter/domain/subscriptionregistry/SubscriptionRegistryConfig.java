package pl.radek.chatter.domain.subscriptionregistry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class SubscriptionRegistryConfig {

    @Bean
    public ConcurrentMap<String, String> getSessionToTopicBean() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ConcurrentMap<String, Set<Subscriber>> getTopicToSubscribersBean() {
        return new ConcurrentHashMap<>();
    }

}

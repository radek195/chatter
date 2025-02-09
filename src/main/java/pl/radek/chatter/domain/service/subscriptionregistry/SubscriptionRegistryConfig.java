package pl.radek.chatter.domain.service.subscriptionregistry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.radek.chatter.domain.model.subscriber.Subscriber;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class SubscriptionRegistryConfig {

    @Bean
    public ConcurrentMap<String, Subscriber> getSessionSubscriberMapBean() {
        return new ConcurrentHashMap<>();
    }

}

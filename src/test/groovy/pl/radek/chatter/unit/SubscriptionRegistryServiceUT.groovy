package pl.radek.chatter.unit

import pl.radek.chatter.domain.model.subscriber.Subscriber
import pl.radek.chatter.domain.service.subscriptionregistry.SubscriptionRegistryService
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SubscriptionRegistryServiceUT extends Specification {
    
    ConcurrentMap<String, Subscriber> sessionSubscriberMap = new ConcurrentHashMap<>()
    
    @Subject
    SubscriptionRegistryService subscriptionRegistryService
    
    def setup() {
        subscriptionRegistryService = new SubscriptionRegistryService.Impl(sessionSubscriberMap)
    }
    
    def "Should add new subscriber"() {
        given: "Test data"
            String sessionId = "8765"
            String topic = "/topic/message/room/abcd"
            Subscriber subscriber = new Subscriber(5, "John", topic)
        
        when: "A user has subscribed"
            subscriptionRegistryService.addSubscription(sessionId, subscriber)
        
        then: "Subscriber should exist under sessionId"
            sessionSubscriberMap.get(sessionId) == subscriber
    }
    
    def "Should add two subscribers to the same topic"() {
        given: "Test data"
            String topic = "/topic/message/room/abcd"
            
            String firstSessionId = "102938"
            Subscriber firstSubscriber = new Subscriber(8, "John", topic)
            
            String secondSessionId = "102939"
            Subscriber secondSubscriber = new Subscriber(9, "Marcus", topic)
        
        when: "Two users have subscribed"
            subscriptionRegistryService.addSubscription(firstSessionId, firstSubscriber)
            subscriptionRegistryService.addSubscription(secondSessionId, secondSubscriber)
        
        then: "Subscribers should exist under different sessionId"
            sessionSubscriberMap.get(firstSessionId) == firstSubscriber
            sessionSubscriberMap.get(secondSessionId) == secondSubscriber
        
    }
    
    def "Should return correct subscriber"() {
        given: "Test data"
            String firstTopic = "/topic/message/room/abcd"
            String secondTopic = "/topic/message/room/lmnop"
            
            String firstSessionId = "102938"
            Subscriber firstSubscriber = new Subscriber(8, "John", firstTopic)
            
            String secondSessionId = "102555"
            Subscriber secondSubscriber = new Subscriber(9, "Marcus", secondTopic)
        
        when: "Two users subscribe to separate topics"
            subscriptionRegistryService.addSubscription(firstSessionId, firstSubscriber)
            subscriptionRegistryService.addSubscription(secondSessionId, secondSubscriber)
        
        then: "Should return correct subscriber"
            Subscriber removedSubscriber = subscriptionRegistryService.removeSubscription(firstSessionId)
            removedSubscriber == firstSubscriber
        
    }
    
}

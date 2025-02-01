package pl.radek.chatter.unit

import pl.radek.chatter.domain.model.subscriber.Subscriber
import pl.radek.chatter.domain.service.subscriptionregistry.SubscriptionRegistryService
import spock.lang.Specification
import spock.lang.Subject

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SubscriptionRegistryServiceUT extends Specification {
    
    ConcurrentMap<String, String> sessionToTopic = new ConcurrentHashMap<>()
    ConcurrentMap<String, Set<Subscriber>> topicToSubscribers = new ConcurrentHashMap<>()
    
    @Subject
    SubscriptionRegistryService subscriptionRegistryService
    
    def setup() {
        sessionToTopic = new ConcurrentHashMap<>()
        topicToSubscribers = new ConcurrentHashMap<>()
        
        subscriptionRegistryService = new SubscriptionRegistryService.Impl(sessionToTopic, topicToSubscribers)
    }
    
    def "Should add new subscriber"() {
        given: "Test data"
            String sessionId = "8765"
            String topic = "/topic/message/room/abcd"
            Subscriber subscriber = new Subscriber(5, "John", sessionId)
        
        when: "A user has subscribed"
            subscriptionRegistryService.addSubscription(topic, subscriber)
        
        then: "Relation session-topic should exist"
            sessionToTopic.get(sessionId) == topic
        
        and: "Relation topic-subscriber should exist"
            topicToSubscribers.get(topic).contains(subscriber)
    }
    
    def "Should add two subscribers to the same topic"() {
        given: "Test data"
            String topic = "/topic/message/room/abcd"
            
            String firstSessionId = "102938"
            Subscriber firstSubscriber = new Subscriber(8, "John", firstSessionId)
            
            String secondSessionId = "102939"
            Subscriber secondSubscriber = new Subscriber(9, "Marcus", secondSessionId)
        
        when: "Two users have subscribed"
            subscriptionRegistryService.addSubscription(topic, firstSubscriber)
            subscriptionRegistryService.addSubscription(topic, secondSubscriber)
        
        then: "One to one session-topic relation should exist"
            sessionToTopic.get(firstSessionId) == topic
            sessionToTopic.get(secondSessionId) == topic
        
        and: "One to many topic-subscriber relation should exist"
            topicToSubscribers.get(topic).contains(firstSubscriber)
            topicToSubscribers.get(topic).contains(secondSubscriber)
    }
    
    def "Should add two subscribers to different topics"() {
        given: "Test data"
            String firstTopic = "/topic/message/room/abcd"
            String secondTopic = "/topic/message/room/lmnop"
            
            String firstSessionId = "102938"
            Subscriber firstSubscriber = new Subscriber(8, "John", firstSessionId)
            
            String secondSessionId = "102555"
            Subscriber secondSubscriber = new Subscriber(9, "Marcus", secondSessionId)
        
        when: "Two users subscribe to separate topics"
            subscriptionRegistryService.addSubscription(firstTopic, firstSubscriber)
            subscriptionRegistryService.addSubscription(secondTopic, secondSubscriber)
        
        then: "Relation session-topic should exist for first user only"
            sessionToTopic.get(firstSessionId) == firstTopic
            topicToSubscribers.get(firstTopic).contains(firstSubscriber)
            !topicToSubscribers.get(firstTopic).contains(secondSubscriber)
        
        and: "Relation session-topic should exist for second user only"
            sessionToTopic.get(secondSessionId) == secondTopic
            topicToSubscribers.get(secondTopic).contains(secondSubscriber)
            !topicToSubscribers.get(secondTopic).contains(firstSubscriber)
    }
    
    def "Should remove correct subscriber"() {
        given: "Test data"
            String firstTopic = "/topic/message/room/abcd"
            String secondTopic = "/topic/message/room/lmnop"
            
            String firstSessionId = "10001"
            Subscriber subscriberA = new Subscriber(8, "John", firstSessionId)
            
            String secondSessionId = "10002"
            Subscriber subscriberB = new Subscriber(9, "Marcus", secondSessionId)
            
            String thirdSessionId = "10003"
            Subscriber subscriberC = new Subscriber(10, "Thomas", thirdSessionId)
        
        and: "Users A and B subscribed to the same topic"
            subscriptionRegistryService.addSubscription(firstTopic, subscriberA)
            subscriptionRegistryService.addSubscription(firstTopic, subscriberB)
        
        and: "User C subscribed to different topic"
            subscriptionRegistryService.addSubscription(secondTopic, subscriberC)
        
        when: "User B has unsubscribed"
            def topicNicknameMap = subscriptionRegistryService.removeSubscription(secondSessionId)
            topicNicknameMap.get("nickname") == "Thomas"
            topicNicknameMap.get("topic") == "/topic/message/room/lmnop"
        
        then: "Relation session-topic and topic-subscriber should exist for user A"
            sessionToTopic.get(firstSessionId) == firstTopic
            topicToSubscribers.get(firstTopic).contains(subscriberA)
        
        and: "Relation session-topic and topic-subscriber should exist for user C"
            sessionToTopic.get(thirdSessionId) == secondTopic
            topicToSubscribers.get(secondTopic).contains(subscriberC)
        
        and: "No relation of topic-subscriber should exist for user B"
            !topicToSubscribers.get(firstTopic).contains(subscriberB)
            !topicToSubscribers.get(secondTopic).contains(subscriberB)
    }
}

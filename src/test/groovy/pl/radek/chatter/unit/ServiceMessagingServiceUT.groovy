package pl.radek.chatter.unit

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.messaging.Message
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import pl.radek.chatter.domain.systemmessager.SystemMessagingService
import spock.lang.Specification
import spock.lang.Subject


class ServiceMessagingServiceUT extends Specification {
    
    def messagingTemplate = Mock(SimpMessagingTemplate)
    def mapper = Mock(ObjectMapper)
    
    @Subject
    SystemMessagingService systemMessagingService = new SystemMessagingService.Impl(messagingTemplate, mapper)
    
    def "Should send system message to correct destination on subscription"() {
        given:
            String destination = "/topic/room/abcd"
            String nickname = "John"
            SessionSubscribeEvent mockEvent = createMockEvent(destination, nickname)
        
        when:
            systemMessagingService.handleNewSubscription(mockEvent)
        
        then:
            1 * messagingTemplate.convertAndSend(destination, _)
    }
    
    def createMockEvent(String destination, String nickname) {
        def accessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE)
        accessor.setNativeHeader("destination", destination)
        accessor.setNativeHeader("nickname", nickname)
        
        Message<byte[]> message = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders())
        return new SessionSubscribeEvent(this, message)
    }
}

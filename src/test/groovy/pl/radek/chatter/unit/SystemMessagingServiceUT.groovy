package pl.radek.chatter.unit

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.messaging.simp.SimpMessagingTemplate
import pl.radek.chatter.domain.service.systemmessager.SystemMessagingService
import spock.lang.Specification
import spock.lang.Subject

class SystemMessagingServiceUT extends Specification {
    
    def messagingTemplate = Mock(SimpMessagingTemplate)
    def mapper = new ObjectMapper()
    
    @Subject
    SystemMessagingService systemMessagingService = new SystemMessagingService.Impl(messagingTemplate, mapper)
    
    def "Should send system message to correct destination on subscription"() {
        given:
            String destination = "/topic/room/abcd"
            String messageContent = "John has joined the room"
            
            String expectedMessage = """{"content":"${messageContent}","senderNickname":null,"type":"SYSTEM"}"""
        when:
            systemMessagingService.sendMessage(destination, messageContent)
        
        then:
            1 * messagingTemplate.convertAndSend(destination, expectedMessage)
    }
}

package pl.radek.chatter.unit

import pl.radek.chatter.domain.service.chatroom.ChatroomService
import pl.radek.chatter.domain.service.user.matching.UserMatchingService
import pl.radek.chatter.infrastructure.exceptions.UserNotFoundException
import pl.radek.chatter.infrastructure.repository.user.UserEntity
import pl.radek.chatter.infrastructure.repository.user.UserRepository
import spock.lang.Specification
import spock.lang.Subject

class ChatroomServiceUT extends Specification {
    
    UserRepository userRepository = Mock()
    UserMatchingService userMatchingService = Mock()
    
    @Subject
    ChatroomService chatroomService
    
    
    def 'Should return existing chatroom'() {
        given: 'Chatroom in the queue exists'
            UUID uuid = UUID.randomUUID()
            Queue<UUID> queue = new LinkedList<>()
            queue.offer(uuid)
            chatroomService = new ChatroomService.Impl(userRepository, userMatchingService, queue)
        
        when: 'Retrieving chatroom'
            UUID retrievedUuid = chatroomService.getChatroom()
        
        then: 'Retrieved id is equal to the existing one'
            retrievedUuid == uuid
        
    }
    
    def 'should not offer new chatroom when queue is not empty'() {
        given: 'Poll for queue is mocked to return UUID'
            def chatroomsMock = Mock(LinkedList<UUID>)
            chatroomService = new ChatroomService.Impl(userRepository, userMatchingService, chatroomsMock)
            1 * chatroomsMock.poll() >> UUID.randomUUID()
        
        when: 'Retrieving chatroom'
            chatroomService.getChatroom()
        
        then: 'Offer should not be called'
            0 * chatroomsMock.offer(_)
    }
    
    def 'Should return new chatroom'() {
        given: 'Chatroom in the queue does not exist'
            chatroomService = new ChatroomService.Impl(userRepository, userMatchingService, new LinkedList<>())
        
        when: 'Retrieving new chatroom'
            UUID retrievedUuid = chatroomService.getChatroom()
        
        then: 'Retrieved id is not null'
            retrievedUuid !== null
        
    }
    
    def 'should offer new chatroom when queue is empty'() {
        given: 'Poll for queue is mocked to return null'
            def chatroomsMock = Mock(LinkedList<UUID>)
            chatroomService = new ChatroomService.Impl(userRepository, userMatchingService, chatroomsMock)
            1 * chatroomsMock.poll() >> null
        
        when: 'Retrieving chatroom'
            chatroomService.getChatroom()
        
        then: 'Offer should not be called'
            1 * chatroomsMock.offer(_)
    }
    
    def setup() {
        chatroomService = new ChatroomService.Impl(userRepository, userMatchingService, Mock(Queue<UUID>))
    }
    
    def 'should return existing chatroomId when matching user is found'() {
        given:
            UserEntity requestingUser = Mock()
            UserEntity matchingUser = Spy(new UserEntity())
            UUID existingChatroomId = UUID.randomUUID()
            matchingUser.setChatroomId(existingChatroomId)
            
            userRepository.findById(_) >> Optional.of(requestingUser)
            userRepository.findAllWithUniqueChatroomIdExcluding(_) >> List.of(matchingUser)
            userMatchingService.findMatchingUsersByPreference(_, _) >> List.of(matchingUser)
        
        when:
            UUID result = chatroomService.getMatchingChatroom(1)
        
        then:
            1 * matchingUser.getChatroomId()
            1 * requestingUser.setChatroomId(existingChatroomId)
            result == existingChatroomId
    }
    
    def 'should return new chatroomId when matching user is not'() {
        given:
            UserEntity requestingUser = Mock()
        
            userRepository.findById(_) >> Optional.of(requestingUser)
            userRepository.findAllWithUniqueChatroomIdExcluding(_) >> List.of()
            userMatchingService.findMatchingUsersByPreference(_, _) >> List.of()
        
        when:
            UUID result = chatroomService.getMatchingChatroom(1)
        
        then:
            1 * requestingUser.setChatroomId(_)
            result !== null
    }
    
    def 'should throw UserNotFoundException when requesting user id cannot be found'() {
        given:
            userRepository.findById(2) >> Optional.empty()
        
        when:
            chatroomService.getMatchingChatroom(2)
        
        then:
            thrown(UserNotFoundException)
    }
    
    
}

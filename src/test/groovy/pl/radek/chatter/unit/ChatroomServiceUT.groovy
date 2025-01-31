package pl.radek.chatter.unit

import pl.radek.chatter.domain.service.chatroom.ChatroomService
import spock.lang.Specification

class ChatroomServiceUT extends Specification {

    ChatroomService chatroomService

    def 'Should return existing chatroom'() {
        given: 'Chatroom in the queue exists'
            UUID uuid = UUID.randomUUID()
            Queue<UUID> queue = new LinkedList<>()
            queue.offer(uuid)
            chatroomService = new ChatroomService.Impl(queue)

        when: 'Retrieving chatroom'
            UUID retrievedUuid = chatroomService.getChatroom()

        then: 'Retrieved id is equal to the existing one'
            retrievedUuid == uuid

    }

    def 'should not offer new chatroom when queue is not empty'() {
        given: 'Poll for queue is mocked to return UUID'
            def chatroomsMock = Mock(LinkedList<UUID>)
            chatroomService = new ChatroomService.Impl(chatroomsMock)
            1 * chatroomsMock.poll() >> UUID.randomUUID()

        when: 'Retrieving chatroom'
            chatroomService.getChatroom()

        then: 'Offer should not be called'
            0 * chatroomsMock.offer(_)
    }

    def 'Should return new chatroom'() {
        given: 'Chatroom in the queue does not exist'
            chatroomService = new ChatroomService.Impl(new LinkedList<>())

        when: 'Retrieving new chatroom'
            UUID retrievedUuid = chatroomService.getChatroom()

        then: 'Retrieved id is not null'
            retrievedUuid !== null

    }

    def 'should offer new chatroom when queue is empty'() {
        given: 'Poll for queue is mocked to return null'
            def chatroomsMock = Mock(LinkedList<UUID>)
            chatroomService = new ChatroomService.Impl(chatroomsMock)
            1 * chatroomsMock.poll() >> null

        when: 'Retrieving chatroom'
            chatroomService.getChatroom()

        then: 'Offer should not be called'
            1 * chatroomsMock.offer(_)
    }

}

package pl.radek.chatter.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import pl.radek.chatter.domain.model.message.Message
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import java.lang.reflect.Type
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ChatMessagingControllerIT extends Specification implements PayloadProvider {

    @LocalServerPort
    int port

    @Shared
    WebSocketStompClient stompClient

    final SEND_MESSAGE_ENDPOINT = "/app/message/"
    final SUB_ROOM_ENDPOINT = "/topic/message/room/"

    private final BlockingQueue<Message> messages = new LinkedBlockingDeque<>()


    def setupSpec() {
        stompClient = new WebSocketStompClient(new StandardWebSocketClient())
        stompClient.messageConverter = new MappingJackson2MessageConverter()
    }

    @Ignore
    //TODO fix this test!!!
    def "Should send message to correct room"() {
        given:
        def session = stompClient.connect(getUrl(), new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS)

        def payload = getMessagePayload()
        def roomId = "25c2caca-dc51-4e0c-ac93-90aefcefe37b"

        session.subscribe(SUB_ROOM_ENDPOINT + roomId, new DefaultStompFrameHandler())

        when:
        session.send(SEND_MESSAGE_ENDPOINT + roomId, payload)

        then:
        String receivedMessage = messages.poll(10, TimeUnit.SECONDS)
        println receivedMessage
    }

    def getUrl() {
        "ws://localhost:${this.port}/websocket"
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        Type getPayloadType(StompHeaders headers) {
            return Message.class
        }

        @Override
        void handleFrame(StompHeaders headers, Object payload) {
            messages.offer((Message) payload)
        }
    }
}

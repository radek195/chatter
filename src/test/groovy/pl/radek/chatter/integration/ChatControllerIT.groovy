package pl.radek.chatter.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.lang.Nullable
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import pl.radek.chatter.config.SpecificationIT
import pl.radek.chatter.interfaces.controller.MessagePayload
import spock.lang.Ignore
import spock.lang.Shared

import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ChatControllerIT extends SpecificationIT implements PayloadProvider {

    @LocalServerPort
    int port

    final SEND_MESSAGE_ENDPOINT = "/app/message/"
    final SUB_ROOM_ENDPOINT = "/topic/message/room/"

    @Shared
    private CompletableFuture<MessagePayload> completableFuture

    def setupSpec() {
        completableFuture = new CompletableFuture<>()
        setupStompClient()
    }

    @Ignore //TODO fix this test!!!
    def "Should send message to correct room"() {
        given:
            def session = stompClient.connectAsync(getUrl(), new StompSessionHandlerAdapter() {})
                    .get(1, TimeUnit.SECONDS)
            def payload = getMessagePayload()
            def roomId = "25c2caca-dc51-4e0c-ac93-90aefcefe37b"

            session.subscribe(SUB_ROOM_ENDPOINT + roomId, new FrameHandler())

        when:
            session.send(SEND_MESSAGE_ENDPOINT, payload)

        then:
            MessagePayload response = completableFuture.get(6, TimeUnit.SECONDS)
            println response
    }

    def getUrl() {
        "ws://localhost:${this.port}/websocket"
    }

    class FrameHandler implements StompFrameHandler {
        @Override
        Type getPayloadType(StompHeaders headers) {
            return MessagePayload.class
        }

        @Override
        void handleFrame(StompHeaders headers, @Nullable Object payload) {
            completableFuture.complete((MessagePayload) payload)
        }
    }
}

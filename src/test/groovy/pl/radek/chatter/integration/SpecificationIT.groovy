package pl.radek.chatter.integration


import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import spock.lang.Shared
import spock.lang.Specification

class SpecificationIT extends Specification {

    @Shared
    WebSocketStompClient stompClient

    def setupStompClient() {
        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()))
        stompClient.setMessageConverter(new MappingJackson2MessageConverter())
    }

    private static List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1)
        transports.add(new WebSocketTransport(new StandardWebSocketClient()))
        return transports
    }

}

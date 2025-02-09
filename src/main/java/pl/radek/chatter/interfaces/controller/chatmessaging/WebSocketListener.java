package pl.radek.chatter.interfaces.controller.chatmessaging;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import pl.radek.chatter.domain.model.subscriber.Subscriber;
import pl.radek.chatter.domain.service.subscriptionfacade.SubscriptionFacade;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketListener {

    private final SubscriptionFacade subscriptionFacade;


    @EventListener
    public void handleWebSocketConnectedListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        String topic = getDestination(getStompHeaderAccessor(event));
        String nickname = getNickname(getStompHeaderAccessor(event));
        String sessionId = getSessionId(getStompHeaderAccessor(event));

        subscriptionFacade.handleSubscription(sessionId, new Subscriber(0, nickname, topic)); //id to be amended
        System.out.println("Received a new web socket subscription. SessionId: " + sessionId);
    }

    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {
        String sessionId = getSessionId(getStompHeaderAccessor(event));
        System.out.println("Received a new web socket unsubscription SessionId: " + sessionId);
        subscriptionFacade.handleUnsubscription(sessionId);

    }

    private StompHeaderAccessor getStompHeaderAccessor(AbstractSubProtocolEvent event) {
        return StompHeaderAccessor.wrap(event.getMessage());
    }

    private String getDestination(StompHeaderAccessor accessor) {
        return accessor.getNativeHeader("destination").get(0);
    }

    private String getNickname(StompHeaderAccessor accessor) {
        List<String> headerList = accessor.getNativeHeader("nickname");
        if (headerList != null && !headerList.isEmpty() && !headerList.get(0).isEmpty()) {
            return headerList.get(0);
        }
        return "Guest";
    }

    private String getSessionId(StompHeaderAccessor accessor) {
        return accessor.getSessionId();
    }
    //    SessionSubscribeEvent mockEvent = createMockEvent(destination, nickname)
//    def createMockEvent(String destination, String nickname) {
//        def accessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE)
//        accessor.setNativeHeader("destination", destination)
//        accessor.setNativeHeader("nickname", nickname)
//
//        Message<byte[]> message = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders())
//        return new SessionSubscribeEvent(this, message)
//    }
}

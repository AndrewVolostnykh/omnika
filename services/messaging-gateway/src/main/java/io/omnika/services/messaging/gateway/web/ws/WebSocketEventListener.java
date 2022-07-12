package io.omnika.services.messaging.gateway.web.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations operations;

    @EventListener
    public void handleSessionConnectEvent(SessionConnectedEvent event) {

    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if (username != null) {
//            WebSocketChatMessage chatMessage = new WebSocketChatMessage();
//            chatMessage.setType("Leave");
//            chatMessage.setSender(username);
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
    }

}

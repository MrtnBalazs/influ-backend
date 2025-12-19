package mrtn.influ;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Called once when the WebSocket is opened
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message) throws Exception {

        System.out.println("Received: " + message.getPayload());

        // Echo example
        session.sendMessage(new TextMessage("ACK: " + message.getPayload()));
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status) {

        System.out.println("WebSocket closed: " + session.getId());
    }
}
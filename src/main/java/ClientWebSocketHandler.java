import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;

public class ClientWebSocketHandler extends TextWebSocketHandler {
//    private static final Logger log = LoggerFactory.getLogger(ClientWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connection opened");

        TextMessage message = new TextMessage("{\n" +
                "  \"event\": \"subscribe\",\n" +
                "  \"pair\": [\n" +
                "    \"XBT/USD\",\n" +
                "    \"XBT/EUR\"\n" +
                "  ],\n" +
                "  \"subscription\": {\n" +
                "    \"name\": \"ticker\"\n" +
                "  }\n" +
                "}");
        System.out.println("Client sends: {}" + message);
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Client connection closed: {}" + status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject obj = new JSONObject(jsonString);
        System.out.println("Client received: {} message " + message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Client transport error: {}" + exception.getMessage());
    }
}

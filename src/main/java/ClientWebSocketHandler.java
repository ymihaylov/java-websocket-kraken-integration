import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ClientWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connection opened");

        SubscribeRequestPayload subscribeRequestPayload = prepareSubscribeRequestPayload();

        TextMessage message = new TextMessage(subscribeRequestPayload.toJson());

        System.out.println("Client sends: " + message);

        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Client connection closed: {}" + status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Client received: {} message " + message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Client transport error: {}" + exception.getMessage());
    }

    private SubscribeRequestPayload prepareSubscribeRequestPayload() {
        SubscribeRequestPayload subscribeRequestPayload = new SubscribeRequestPayload();

        subscribeRequestPayload.addCurrencyPair("BTC/USD");
        subscribeRequestPayload.addCurrencyPair("ETH/USD");

        subscribeRequestPayload.addSubscriptionSetting("name", "book");

        return subscribeRequestPayload;
    }
}

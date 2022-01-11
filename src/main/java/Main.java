import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Scanner;

public class Main {
    private static String URL = "ws://ws.kraken.com";

    public static void main(String[] args) {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
            new StandardWebSocketClient(),
            new ClientWebSocketHandler(),
 "ws://ws.kraken.com"
        );

        manager.start();
        manager.setAutoStartup(true);

//        WebSocketClient client = new StandardWebSocketClient();
//
//        WebSocketStompClient stompClient = new WebSocketStompClient(client);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        stompClient.connect(URL, sessionHandler);
//
        new Scanner(System.in).nextLine();
    }
}

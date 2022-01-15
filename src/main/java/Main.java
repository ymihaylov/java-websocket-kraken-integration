import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Scanner;

public class Main {
    private static final String KRAKEN_URL = "ws://ws.kraken.com";

    public static void main(String[] args) {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
            new StandardWebSocketClient(),
            new ClientWebSocketHandler(),
            KRAKEN_URL
        );

        manager.start();

        new Scanner(System.in).nextLine();
    }
}

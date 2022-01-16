import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Scanner;

public class Main {
    private static final String KRAKEN_URL = "ws://ws.kraken.com";
    private static WebSocketConnectionManager krakenWebSocketConnectionManager;

    public static void main(String[] args) {
        WebSocketConnectionManager krakenWebSocket = getKrakenWebSocketConnectionManager();
        krakenWebSocket.start();

        new Scanner(System.in).nextLine();
    }

    private static WebSocketConnectionManager getKrakenWebSocketConnectionManager()
    {
        if (krakenWebSocketConnectionManager == null) {
            krakenWebSocketConnectionManager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                new KrakenClientWebSocketHandler(),
                KRAKEN_URL
            );
        }

        return krakenWebSocketConnectionManager;
    }
}

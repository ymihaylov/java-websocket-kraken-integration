import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static final int TIMER_PERIOD = 5000; // milliseconds
    private static final String KRAKEN_URL = "ws://ws.kraken.com";

    private static WebSocketConnectionManager krakenWebSocketConnectionManager;

    public static void main(String[] args) {
        WebSocketConnectionManager krakenWebSocket = getKrakenWebSocketConnectionManager();
        krakenWebSocket.start();

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Here");
            }
        }, 0, TIMER_PERIOD);

//        new Scanner(System.in).nextLine();
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

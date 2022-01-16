import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static final int TIMER_PERIOD = 2000; // milliseconds
    private static final String KRAKEN_URL = "ws://ws.kraken.com";

    private static WebSocketConnectionManager krakenWebSocketConnectionManager;

    public static void main(String[] args) {
        WebSocketConnectionManager krakenWebSocket = getKrakenWebSocketConnectionManager();
        krakenWebSocket.start();

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            if (KrakenOrderBookDataUtil.getInstance().getOrderBookData().isEmpty()) {
                return;
            }

            KrakenOrderBookDataUtil.getInstance().getOrderBookData().forEach((currencyPairs, data) -> {
                System.out.println("<------------------------------------>");
                System.out.println("Currency pairs: " + currencyPairs);

                HashMap<KrakenOrderBookEntryType, ArrayList<KrakenOrderBookEntry>> orderBookEntryData = (HashMap) data;
                orderBookEntryData.forEach((entryType, orderBookEntries) -> {
                    System.out.println(entryType.toString().toLowerCase() + "s"); // It's hacky and I know it (music)

                    orderBookEntries.forEach((entry) -> System.out.println(entry));

                    String best = entryType == KrakenOrderBookEntryType.ASK
                            ? orderBookEntries.get(0).toString()
                            : orderBookEntries.get(orderBookEntries.size() - 1).toString();

                    System.out.println("Best " + entryType.toString() + ": " + best);
                });

                System.out.println(">------------------------------------<");
            });

            KrakenOrderBookDataUtil.getInstance().clearOrderBookData();
            }
        }, 0, TIMER_PERIOD);
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

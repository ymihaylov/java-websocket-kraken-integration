import kraken_websocket_client.KrakenClientWebSocketHandler;
import orderbook.OrderBookEntry;
import orderbook.OrderBookEntryType;
import orderbook.OrderBookEntryComparator;
import orderbook.OrderBookDataUtil;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    /**
     * - Developed with Java 15 and gradle dependency management tool
     * - Used IntelliJ 2021.2.3 (Ultimate Edition)
     * - On MacBook Air Late 2013 with macOS Big Sur version 11.6
     */
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
                if (OrderBookDataUtil.getInstance().getOrderBookData().isEmpty()) {
                    return;
                }

                OrderBookDataUtil.getInstance().getOrderBookData().forEach((currencyPairs, data) -> {
                    System.out.println("<------------------------------------>");
                    System.out.println("Currency pairs: " + currencyPairs);

                    HashMap<OrderBookEntryType, ArrayList<OrderBookEntry>> orderBookEntryData = (HashMap) data;
                    orderBookEntryData.forEach((entryType, orderBookEntries) -> {
                        System.out.println(entryType.toString() + "s:");

                        Collections.sort(orderBookEntries, new OrderBookEntryComparator());

                        orderBookEntries.forEach((entry) -> System.out.println(entry));

                        String best = entryType == OrderBookEntryType.BID
                                ? orderBookEntries.get(0).toString()
                                : orderBookEntries.get(orderBookEntries.size() - 1).toString();

                        System.out.println("Best " + entryType + ": " + best + "\n");
                    });

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println(formatter.format(now));

                    System.out.println(">------------------------------------<");
                });

                OrderBookDataUtil.getInstance().clearOrderBookData();
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

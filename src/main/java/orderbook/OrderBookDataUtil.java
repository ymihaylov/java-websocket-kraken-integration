package orderbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderBookDataUtil {
    private static OrderBookDataUtil instance;

    // Is it too complex structure?
    // XBT/USD -> (BID -> (Entry1, Entry2, ...))
    private Map<String, HashMap<OrderBookEntryType, ArrayList<OrderBookEntry>>> orderBookData;

    private OrderBookDataUtil() {
        orderBookData = new HashMap<String, HashMap<OrderBookEntryType, ArrayList<OrderBookEntry>>>();
    }

    public void putOrderBookData(String currencyPair, OrderBookEntryType type, ArrayList orderBookEntries) {
        if ( ! orderBookData.containsKey(currencyPair)) {
            orderBookData.put(currencyPair, new HashMap<>());
        }

        if ( ! orderBookData.get(currencyPair).containsKey(type)) {
            orderBookData.get(currencyPair).put(type, new ArrayList<>());
        }

        orderBookData.get(currencyPair).get(type).addAll(orderBookEntries);
    }

    public void clearOrderBookData() {
        orderBookData.clear();
    }

    public Map getOrderBookData() {
        return orderBookData;
    }

    public static OrderBookDataUtil getInstance() {
        if (instance == null) {
            instance = new OrderBookDataUtil();
        }

        return instance;
    }
}

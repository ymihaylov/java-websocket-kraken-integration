import java.util.ArrayList;
import java.util.HashMap;

public class KrakenOrderBookDataUtil {
    private static KrakenOrderBookDataUtil instance;

    // Is it too complex structure?
    private HashMap<String, HashMap<KrakenOrderBookEntryType, ArrayList<KrakenOrderBookEntry>>> orderBookData;

    private KrakenOrderBookDataUtil() {
        orderBookData = new HashMap<String, HashMap<KrakenOrderBookEntryType, ArrayList<KrakenOrderBookEntry>>>();
    }

    public void putOrderBookData(String currencyPair, KrakenOrderBookEntryType type, ArrayList orderBookEntries) {
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

    public HashMap getOrderBookData() {
        return orderBookData;
    }

    public static KrakenOrderBookDataUtil getInstance() {
        if (instance == null) {
            instance = new KrakenOrderBookDataUtil();
        }

        return instance;
    }
}

import java.util.Comparator;

public class OrderBookEntryComparator implements Comparator<KrakenOrderBookEntry> {
    @Override
    public int compare(KrakenOrderBookEntry o1, KrakenOrderBookEntry o2) {
        Double priceDiff = o1.getPrice() - o2.getPrice();
        return priceDiff == 0 ? 0 : (priceDiff > 0 ? -1 : 1);
    }
}

package orderbook;

import java.util.Comparator;

public class OrderBookEntryComparator implements Comparator<OrderBookEntry> {
    @Override
    public int compare(OrderBookEntry o1, OrderBookEntry o2) {
        Double priceDiff = o1.getPrice() - o2.getPrice();
        return priceDiff == 0 ? 0 : (priceDiff > 0 ? -1 : 1);
    }
}

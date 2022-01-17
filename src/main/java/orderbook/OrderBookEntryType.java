package orderbook;

public enum OrderBookEntryType {
    BID,
    ASK;

    public static OrderBookEntryType getOrderBookEntryTypeByKey(String orderBookDataKey) {
        if (orderBookDataKey == "a" || orderBookDataKey == "as") {
            return OrderBookEntryType.ASK;
        } else if (orderBookDataKey == "b" || orderBookDataKey == "bid") {
            return OrderBookEntryType.BID;
        }

        throw new RuntimeException("Invalid orderBookDataKey");
    }
}

public enum KrakenOrderBookEntryType {
    BID,
    ASK;

    public static KrakenOrderBookEntryType getOrderBookEntryTypeByKey(String orderBookDataKey) {
        if (orderBookDataKey == "a" || orderBookDataKey == "as") {
            return KrakenOrderBookEntryType.ASK;
        } else if (orderBookDataKey == "b" || orderBookDataKey == "bid") {
            return KrakenOrderBookEntryType.BID;
        }

        throw new RuntimeException("Invalid orderBookDataKey");
    }
}

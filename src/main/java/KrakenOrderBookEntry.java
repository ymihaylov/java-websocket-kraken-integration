public class KrakenOrderBookEntry {
    private Double price;
    private Double amount;
    private Long time;

    private String currencyPair;
    private KrakenOrderBookEntryType orderBookEntryType;

    public KrakenOrderBookEntry(Double price, Double amount, String currencyPair, KrakenOrderBookEntryType orderBookEntryType) {
        this.price = price;
        this.amount = amount;
        this.currencyPair = currencyPair;
        this.orderBookEntryType = orderBookEntryType;

        this.time = System.currentTimeMillis();
    }

    public KrakenOrderBookEntry(Double price, Double amount, String currencyPair, KrakenOrderBookEntryType orderBookEntryType, Long time) {
        this.price = price;
        this.amount = amount;
        this.currencyPair = currencyPair;
        this.orderBookEntryType = orderBookEntryType;

        this.time = time;
    }

    @Override
    public String toString() {
        return "[ " + price + ", " + amount + " ]";
    }
}

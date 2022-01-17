package orderbook;

public class OrderBookEntry {
    private Double price;
    private Double amount;
    private Long time;

    private String currencyPair;
    private OrderBookEntryType orderBookEntryType;

    public OrderBookEntry(Double price, Double amount, String currencyPair, OrderBookEntryType orderBookEntryType) {
        this.price = price;
        this.amount = amount;
        this.currencyPair = currencyPair;
        this.orderBookEntryType = orderBookEntryType;

        this.time = System.currentTimeMillis();
    }

    public OrderBookEntry(Double price, Double amount, String currencyPair, OrderBookEntryType orderBookEntryType, Long time) {
        this.price = price;
        this.amount = amount;
        this.currencyPair = currencyPair;
        this.orderBookEntryType = orderBookEntryType;

        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "[ " + price + ", " + amount + " ]";
    }
}

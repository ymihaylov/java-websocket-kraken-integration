package kraken_websocket_client;

import orderbook.OrderBookEntry;
import orderbook.OrderBookEntryType;
import orderbook.OrderBookDataUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class KrakenClientWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connection opened!");

        KrakenSubscribeRequestPayload krakenSubscribeRequestPayload = prepareSubscribeRequestPayload();
        TextMessage subscribeMessage = new TextMessage(krakenSubscribeRequestPayload.toJson());

        System.out.println("Client sends: " + subscribeMessage);
        session.sendMessage(subscribeMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Client connection closed: " + status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // System.out.println("Client received an message: " + message);

        JSONArray payloadJsonArray;
        try {
            payloadJsonArray = new JSONArray(message.getPayload());
        } catch (JSONException e) {
            // @TODO: Do something smarter: logging, handling etc.
            return;
        }

        Map<String, Object> orderBookData = payloadJsonArray.getJSONObject(1).toMap();

        String currencyPair = payloadJsonArray.getString(3);

        // I know, I am skipping the first *combined* call from WebSocket server with the latest asks and bids. ;[
        Optional<String> optionalOrderBookDataKey = getOrderBookDataKey(orderBookData); // "a" or "b"
        if (optionalOrderBookDataKey.isEmpty()) {
            return;
        }

        String orderBookDataKey = optionalOrderBookDataKey.get();

        OrderBookEntryType orderBookEntryType = OrderBookEntryType.getOrderBookEntryTypeByKey(orderBookDataKey);

        ArrayList<OrderBookEntry> newOrderBookEntries = prepareNewOrderBookEntries(
            currencyPair,
            orderBookEntryType,
            (ArrayList) orderBookData.get(orderBookDataKey)
        );

        OrderBookDataUtil.getInstance().putOrderBookData(currencyPair, orderBookEntryType, newOrderBookEntries);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Client transport error: {}" + exception.getMessage());
    }

    private KrakenSubscribeRequestPayload prepareSubscribeRequestPayload() {
        KrakenSubscribeRequestPayload krakenSubscribeRequestPayload = new KrakenSubscribeRequestPayload();

        // Pairs and subscription settings should be stored in config files for example (should be configurable).
        // Also, they should have validation before setting them in payload object etc.
        // but for the purposes of this task I think the following way is okayish.
        krakenSubscribeRequestPayload.addCurrencyPair("BTC/USD");
        krakenSubscribeRequestPayload.addCurrencyPair("ETH/USD");

        krakenSubscribeRequestPayload.addSubscriptionSetting("name", "book");

        return krakenSubscribeRequestPayload;
    }

    private Optional<String> getOrderBookDataKey(Map orderBookData)
    {
//        String[] possibleKeys = {"a", "as", "b", "bs"};
        String[] possibleKeys = {"a", "b"};

        return Arrays
            .stream(possibleKeys)
            .filter((possibleKey) -> orderBookData.containsKey(possibleKey))
            .findFirst();
    }

    private ArrayList prepareNewOrderBookEntries(String currencyPair, OrderBookEntryType orderBookEntryType, ArrayList entriesByPayload) {
        ArrayList<OrderBookEntry> newOrderBookEntries = new ArrayList();
        
        entriesByPayload.stream().forEach((entry) -> {
            String priceStr = (String) ((ArrayList) entry).get(0);
            String amountStr = (String) ((ArrayList) entry).get(1);

            Double price = Double.parseDouble(priceStr);
            Double amount = Double.parseDouble(amountStr);

            OrderBookEntry orderBookEntry = new OrderBookEntry(price, amount, currencyPair, orderBookEntryType);

            newOrderBookEntries.add(orderBookEntry);
        });

        return newOrderBookEntries;
    }
}

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class KrakenClientWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Client connection opened!");

        SubscribeRequestPayload subscribeRequestPayload = prepareSubscribeRequestPayload();
        TextMessage message = new TextMessage(subscribeRequestPayload.toJson());

        System.out.println("Client sends: " + message);
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Client connection closed: " + status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
//        System.out.println("Client received an message: " + message);

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
        String orderBookDataKey = getOrderBookDataKey(orderBookData).get(); // "a" or "b"
        KrakenOrderBookEntryType orderBookEntryType = KrakenOrderBookEntryType.getOrderBookEntryType(orderBookDataKey);

        ArrayList<KrakenOrderBookEntry> newOrderBookEntries = prepareNewOrderBookEntries(
            currencyPair,
            orderBookEntryType,
            (ArrayList) orderBookData.get(orderBookDataKey)
        );

        KrakenOrderBookDataUtil.getInstance().putOrderBookData(currencyPair, orderBookEntryType, newOrderBookEntries);

        System.out.println("Here");

        // @TODO Validate payload
        // @TODO Handle first request with bs and as
//        KrakenOrderBookDataUtil.getInstance().incrementInteger();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.out.println("Client transport error: {}" + exception.getMessage());
    }

    private SubscribeRequestPayload prepareSubscribeRequestPayload() {
        SubscribeRequestPayload subscribeRequestPayload = new SubscribeRequestPayload();

        // Pairs and subscription settings should be stored in config files for example (should be configurable).
        // Also, they should have validation before setting them in payload object etc.
        // but for the purposes of this task I think the following way is okayish.
        subscribeRequestPayload.addCurrencyPair("BTC/USD");
        subscribeRequestPayload.addCurrencyPair("ETH/USD");

        subscribeRequestPayload.addSubscriptionSetting("name", "book");

        return subscribeRequestPayload;
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

    private ArrayList prepareNewOrderBookEntries(String currencyPair, KrakenOrderBookEntryType entryType, ArrayList entriesByPayload) {
        ArrayList<KrakenOrderBookEntry> newOrderBookEntries = new ArrayList();
        
        entriesByPayload.stream().forEach((entry) -> {
            String priceStr = (String) ((ArrayList) entry).get(0);
            String amountStr = (String) ((ArrayList) entry).get(1);

            Double price = Double.parseDouble(priceStr);
            Double amount = Double.parseDouble(amountStr);

            KrakenOrderBookEntry orderBookEntry = new KrakenOrderBookEntry(price, amount, currencyPair, entryType);

            newOrderBookEntries.add(orderBookEntry);
        });

        return newOrderBookEntries;
    }
}

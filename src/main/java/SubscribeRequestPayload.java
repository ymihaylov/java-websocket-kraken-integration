import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SubscribeRequestPayload {
    private static final String EVENT_NAME = "subscribe";

    private Set<String> currencyPairs;
    private Map<String, String> subscriptionSettings;

    public SubscribeRequestPayload() {
        currencyPairs = new HashSet<>();
        subscriptionSettings = new HashMap<>();
    }

    public void addCurrencyPair(String currencyPair) {
        currencyPairs.add(currencyPair);
    }

    public void addSubscriptionSetting(String settingKey, String settingValue) {
        subscriptionSettings.put(settingKey, settingValue);
    }

    public String toJson() {
        String currencyPairsToString = convertCurrencyPairsToString();
        String subscriptionSetting = convertSettingsToString();

        return """
                {
                    "event": "EVENT",
                    "pair": [
                      "BTC/USD",
                      "ETH/USD"
                    ],
                    "subscription": {
                      "name": "book"
                    }
                }
                """;
    }

    // I know I can use json libraries for the methods bellow ....
    private String convertCurrencyPairsToString() {
        Set<String> currencyPairsWithQuotes = currencyPairs.stream().map((String pair) -> {
            return "\"" + pair + "\"";
        }).collect(Collectors.toSet());

        return "[" + String.join(", ", currencyPairsWithQuotes) + "]";
    }

    private String convertSettingsToString() {
        String settingsString = "";

        settingsString += "{";
        for (Map.Entry entry : subscriptionSettings.entrySet()) {
            settingsString += "\"" + entry.getKey() + "\"";
        }

        return settingsString;
    }
}

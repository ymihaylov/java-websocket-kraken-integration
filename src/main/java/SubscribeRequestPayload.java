import java.util.*;
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
        return """
            {
                "event": "%s",
                "pair": %s,
                "subscription": %s
            }""".formatted(
                EVENT_NAME,
                convertCurrencyPairsToJsonString(),
                convertSettingsToJsonString()
            );
    }

    // I know that I could use third-party library (google/gson or other) for the methods bellow.
    // I just wanted to play with Stream API and Strings
    private String convertCurrencyPairsToJsonString() {
        Set<String> currencyPairsWithQuotes = currencyPairs
                .stream()
                .map((String pair) -> "\"" + pair + "\"")
                .collect(Collectors.toSet());

        return "[" + String.join(", ", currencyPairsWithQuotes) + "]";
    }

    private String convertSettingsToJsonString() {
        List<String> settingsStrings = subscriptionSettings
                .entrySet()
                .stream()
                .map((e) -> "\"" + e.getKey() + "\": " + "\"" + e.getValue() + "\"")
                .collect(Collectors.toList());

        return """
                {%s}
                """.formatted(String.join(", ", String.join(", ", settingsStrings)));
    }
}

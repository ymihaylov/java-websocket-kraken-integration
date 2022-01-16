public class KrakenOrderBookDataUtil {
    private static KrakenOrderBookDataUtil instance;

    private Integer test = 0;

    private KrakenOrderBookDataUtil()
    {

    }

    public void incrementInteger()
    {
        test++;
    }

    public Integer getInteger()
    {
        return test;
    }

    public static KrakenOrderBookDataUtil getInstance()
    {
        if (instance == null) {
            instance = new KrakenOrderBookDataUtil();
        }

        return instance;
    }
}

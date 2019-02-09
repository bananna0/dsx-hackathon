package uk.dsxt.matchingengine;

public class BaseTest {
    public static final String eurusd = "EURUSD";
    public static final String addressClient1 = "address1";
    public static final String addressClient2 = "address2";
    public static final String signClient1 = "sign1";
    public static final String signClient2 = "sign2";
    public TestExchange testExchange = new TestExchange();

    class TestExchange implements ExchangeInterface {

        @Override
        public OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
            return null;
        }

        @Override
        public boolean cancelOrder(long clientOrderId) {
            return false;
        }
    }
}

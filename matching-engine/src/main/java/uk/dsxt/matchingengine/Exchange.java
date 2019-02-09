package uk.dsxt.matchingengine;

public class Exchange implements ExchangeInterface {

    @Override
    public OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
        return null;
    }

    @Override
    public boolean cancelOrder(long clientOrderId) {
        return false;
    }

}

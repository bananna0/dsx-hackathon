package uk.dsxt.matchingengine;

import uk.dsxt.matchingengine.datamodel.OpenOrderResult;

public interface ExchangeInterface {

    OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign);

    boolean cancelOrder(long clientOrderId);

}

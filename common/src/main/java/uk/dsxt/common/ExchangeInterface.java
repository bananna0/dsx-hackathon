package uk.dsxt.common;


import uk.dsxt.common.datamodel.OpenOrderResult;
import uk.dsxt.common.datamodel.OrderDirection;

public interface ExchangeInterface {

    OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign);

    boolean cancelOrder(long clientOrderId, String address, String sign);

}

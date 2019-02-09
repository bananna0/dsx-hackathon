package uk.dsxt.exchange_api;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.matchingengine.ExchangeInterface;
import uk.dsxt.matchingengine.OpenOrderResult;
import uk.dsxt.matchingengine.OrderDirection;

@Log4j2
public class MockExchange implements ExchangeInterface {
    @Override
    public OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
        log.info("openOrder, currencyPair={}, amount={}, price={}, direction={}, clientOrderId={}, address={}, sign={}",
                currencyPair, amount, price, direction, clientOrderId, address, sign);
        return OpenOrderResult.SUCCESS;
    }

    @Override
    public boolean cancelOrder(long clientOrderId) {
        log.info("cancelOrder, clientOrderId={}", clientOrderId);
        return true;
    }
}

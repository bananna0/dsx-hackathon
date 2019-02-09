package uk.dsxt.exchange_api;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.common.ExchangeInterface;
import uk.dsxt.common.datamodel.OrderDirection;
import uk.dsxt.common.datamodel.OpenOrderResult;
import uk.dsxt.common.datamodel.OpenOrderResultCode;

@Log4j2
public class MockExchange implements ExchangeInterface {
    @Override
    public OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
        log.info("openOrder, currencyPair={}, amount={}, price={}, direction={}, clientOrderId={}, address={}, sign={}",
                currencyPair, amount, price, direction, clientOrderId, address, sign);
        return new OpenOrderResult(1, OpenOrderResultCode.SUCCESS);
    }

    @Override
    public boolean cancelOrder(long clientOrderId, String address, String sign) {
        log.info("cancelOrder, clientOrderId={}, address={}, sign={}", clientOrderId, address, sign);
        return true;
    }
}

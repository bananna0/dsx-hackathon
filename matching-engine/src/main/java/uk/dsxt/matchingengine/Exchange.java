package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.matchingengine.datamodel.OpenOrderResult;
import uk.dsxt.matchingengine.datamodel.OpenOrderResultCode;
import uk.dsxt.matchingengine.datamodel.OpenOrderResultInternal;
import uk.dsxt.matchingengine.datamodel.OrderBook;

@Log4j2
public class Exchange implements ExchangeInterface {

    private MatchingEngine engine;
    private SmartContractSender smartContractSender;

    public Exchange(SmartContractInterface smartContractImpl) {
        engine = new MatchingEngine("EURUSD");
        smartContractSender = new SmartContractSender(smartContractImpl);
    }

    @Override
    public OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
        log.debug("openOrder, currencyPair={}, amount={}, price={}, direction={}, clientOrderId={}, address={}, sign={}",
                currencyPair, amount, price, direction, clientOrderId, address, sign);
        if (!currencyPair.equalsIgnoreCase("EURUSD")) {
            log.warn("Unsupported currency pair: {}", currencyPair);
            return new OpenOrderResult(-1, OpenOrderResultCode.FAILED);
        }

        OpenOrderResultInternal openOrderResultInternal = engine.openOrder(currencyPair, amount, price, direction, clientOrderId, address, sign);
        smartContractSender.sendDeals(openOrderResultInternal);
        return openOrderResultInternal.toOpenOrderResult();
    }

    @Override
    public boolean cancelOrder(long clientOrderId, String address, String sign) {
        log.debug("cancelOrder, clientOrderId={}, address={}, sign={}", clientOrderId, address, sign);
        return engine.cancelOrder(clientOrderId, address, sign);
    }

    public OrderBook getOrderbook(String currencyPair) {
        if (!currencyPair.equalsIgnoreCase("EURUSD")) {
            log.warn("Unsupported currency pair: {}", currencyPair);
            return null;
        }
        return engine.getOrderBook();
    }

}

package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.matchingengine.datamodel.Order;

@Log4j2
public class Exchange implements ExchangeInterface {

    private MatchingEngine engine;

    public Exchange() {
        engine = new MatchingEngine("EURUSD");
    }

    @Override
    public OpenOrderResult openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
        if (!currencyPair.equalsIgnoreCase("EURUSD")) {
            log.warn("Unsupported currency pair: {}", currencyPair);
            return OpenOrderResult.FAILED;
        }

        return engine.openOrder(new Order(currencyPair, amount, 0, price, direction, clientOrderId, address, sign));
    }

    @Override
    public boolean cancelOrder(long clientOrderId) {
        return false;
    }

}

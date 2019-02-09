package uk.dsxt.matchingengine;

import uk.dsxt.common.datamodel.OrderDirection;

public class FilledOrdersTests extends BaseTest {

    public void fullyFilledOrders() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 2, addressClient2, signClient2);
    }
}

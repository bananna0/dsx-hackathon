package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.matchingengine.datamodel.Trade;

public class CreateDeals extends BaseTest {

    @Test
    public void twoOrdersMatchingCorrectAmount() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 2, addressClient2, signClient2);
        Trade trade = smartContractInterfaceMock.trades.get(0);
        Assert.assertEquals(trade.getAmount(), 1);
    }
}

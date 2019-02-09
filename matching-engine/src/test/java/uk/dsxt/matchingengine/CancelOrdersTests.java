package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CancelOrdersTests extends BaseTest{

    @Test
    public void cancelSellLimitOrderSuccess() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertTrue(testExchange.cancelOrder(1));
    }

    @Test
    public void cancelBuyLimitOrderSuccess() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertTrue(testExchange.cancelOrder(1));
    }
}

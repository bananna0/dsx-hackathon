package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateCorrectOrdersTests extends BaseTest {
    @Test
    public void createSellLimitOrderSuccess() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResult.SUCCESS);
    }

    @Test
    public void createBuyLimitOrderSuccess() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResult.SUCCESS);
    }
}

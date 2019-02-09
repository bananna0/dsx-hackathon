package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.matchingengine.datamodel.OpenOrderResultCode;

public class CreateIncorrectParamsOrdersTests extends BaseTest {

    @Test
    public void createBuyLimitOrderFailedByNegativePrice() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, 1, -1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByNegativePrice() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, 1, -1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createBuyLimitOrderFailedByNegativeVolume() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, -1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByNegativeVolume() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, -1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createBuyLimitOrderFailedByZeroPrice() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, 1, 0, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByZeroPrice() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, 1, 0, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createBuyLimitOrderFailedByZeroVolume() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, 0, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByZeroVolume() {
        OpenOrderResultCode result = testExchange.openOrder(eurusd, 0, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result, OpenOrderResultCode.FAILED);
    }


}

package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.common.datamodel.OpenOrderResult;
import uk.dsxt.common.datamodel.OpenOrderResultCode;
import uk.dsxt.common.datamodel.OrderDirection;

public class CreateIncorrectParamsOrdersTests extends BaseTest {

    @Test
    public void createBuyLimitOrderFailedByNegativePrice() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, -1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByNegativePrice() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, -1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createBuyLimitOrderFailedByNegativeVolume() {
        OpenOrderResult result = testExchange.openOrder(eurusd, -1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByNegativeVolume() {
        OpenOrderResult result = testExchange.openOrder(eurusd, -1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createBuyLimitOrderFailedByZeroPrice() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, 0, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByZeroPrice() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, 0, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createBuyLimitOrderFailedByZeroVolume() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 0, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }

    @Test
    public void createSellLimitOrderFailedByZeroVolume() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 0, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.FAILED);
    }


}

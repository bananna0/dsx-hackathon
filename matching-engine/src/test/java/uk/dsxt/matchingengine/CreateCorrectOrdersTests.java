package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.common.datamodel.OpenOrderResult;
import uk.dsxt.common.datamodel.OpenOrderResultCode;
import uk.dsxt.common.datamodel.OrderDirection;

public class CreateCorrectOrdersTests extends BaseTest {
    @Test
    public void createSellLimitOrderSuccess() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.SUCCESS);
    }

    @Test
    public void createBuyLimitOrderSuccess() {
        OpenOrderResult result = testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        Assert.assertEquals(result.getCode(), OpenOrderResultCode.SUCCESS);
    }
}

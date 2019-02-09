package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.matchingengine.datamodel.Trade;

import java.util.ArrayList;

public class CreateDeals extends BaseTest {

    @Test
    public void twoOrdersMatchingCorrectAmount() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 2, addressClient2, signClient2);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getAmount(), 1);
    }

    @Test
    public void moreThanOneDeal() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 2, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 3, addressClient2, signClient2);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.size(), 2);
        Assert.assertEquals(trades.get(0).getAmount(), 1);
        Assert.assertEquals(trades.get(1).getAmount(), 1);
    }
}

package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.dsxt.matchingengine.datamodel.Trade;

import java.util.ArrayList;

public class CreateDeals extends BaseTest {

    @BeforeMethod
    public void init(){
        smartContractInterfaceMock = new SmartContractInterfaceMockImpl();
        testExchange = new Exchange(smartContractInterfaceMock);
    }

    @Test
    public void twoOrdersMatchingCorrectAmount() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 2, addressClient2, signClient2);
        waitForTradesCount(1, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getAmount(), 1);
    }

    @Test
    public void twoOrdersMatchingCorrectPrice() {
        testExchange.openOrder(eurusd, 1, 2, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 3, OrderDirection.BUY, 2, addressClient2, signClient2);
        waitForTradesCount(1, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getPrice(), 2);
    }

    @Test
    public void moreThanOneDeal() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 2, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 3, addressClient2, signClient2);
        waitForTradesCount(2, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.size(), 2);
        Assert.assertEquals(trades.get(0).getAmount(), 1);
        Assert.assertEquals(trades.get(1).getAmount(), 1);
    }
}

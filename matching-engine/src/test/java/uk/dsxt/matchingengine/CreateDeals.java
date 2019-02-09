package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.dsxt.common.datamodel.OrderDirection;
import uk.dsxt.common.datamodel.Trade;

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
    public void twoOrdersMatchingCorrectPriceBuyAgressor() {
        testExchange.openOrder(eurusd, 1, 2, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 3, OrderDirection.BUY, 2, addressClient2, signClient2);
        waitForTradesCount(1, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getPrice(), 2);
    }

    @Test
    public void twoOrdersMatchingCorrectPriceSellAgressor() {
        testExchange.openOrder(eurusd, 1, 2, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 2, addressClient2, signClient2);
        waitForTradesCount(1, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getPrice(), 2);
    }

    @Test
    public void moreThanOneDealSellSide() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 2, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 3, addressClient2, signClient2);
        waitForTradesCount(2, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.size(), 2);
        Assert.assertEquals(trades.get(0).getAmount(), 1);
        Assert.assertEquals(trades.get(1).getAmount(), 1);
    }

    @Test
    public void moreThanOneDealBuySide() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 2, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.SELL, 3, addressClient2, signClient2);
        waitForTradesCount(2, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.size(), 2);
        Assert.assertEquals(trades.get(0).getAmount(), 1);
        Assert.assertEquals(trades.get(1).getAmount(), 1);
    }

    @Test
    public void partlyFilledBuyOrderHasDeal() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 5, 1, OrderDirection.BUY, 2, addressClient2, signClient2);
        waitForTradesCount(1, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getAmount(), 1);
    }

    @Test
    public void partlyFilledSellOrderHasDeal() {
        testExchange.openOrder(eurusd, 1, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 5, 1, OrderDirection.SELL, 2, addressClient2, signClient2);
        waitForTradesCount(1, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getAmount(), 1);
    }

    @Test
    public void differentPricesDealsSellSide(){
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 2, addressClient2, signClient2);
        testExchange.openOrder(eurusd, 6, 5, OrderDirection.SELL, 3, addressClient2, signClient2);
        testExchange.openOrder(eurusd, 12, 5, OrderDirection.BUY, 4, addressClient2, signClient2);
        waitForTradesCount(3, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getAmount(), 2);
        Assert.assertEquals(trades.get(0).getPrice(), 1);
        Assert.assertEquals(trades.get(1).getAmount(), 4);
        Assert.assertEquals(trades.get(1).getPrice(), 3);
        Assert.assertEquals(trades.get(2).getAmount(), 6);
        Assert.assertEquals(trades.get(2).getPrice(), 5);
    }

    @Test
    public void differentPricesDealsBuySide(){
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.BUY, 2, addressClient2, signClient2);
        testExchange.openOrder(eurusd, 6, 5, OrderDirection.BUY, 3, addressClient2, signClient2);
        testExchange.openOrder(eurusd, 12, 1, OrderDirection.SELL, 4, addressClient2, signClient2);
        waitForTradesCount(3, 1, 100);
        ArrayList<Trade> trades = smartContractInterfaceMock.trades;
        Assert.assertEquals(trades.get(0).getAmount(), 6);
        Assert.assertEquals(trades.get(0).getPrice(), 5);
        Assert.assertEquals(trades.get(1).getAmount(), 4);
        Assert.assertEquals(trades.get(1).getPrice(), 3);
        Assert.assertEquals(trades.get(2).getAmount(), 2);
        Assert.assertEquals(trades.get(2).getPrice(), 1);
    }

}

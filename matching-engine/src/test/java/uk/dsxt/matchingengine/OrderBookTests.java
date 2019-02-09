package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.dsxt.common.datamodel.OrderBook;
import uk.dsxt.common.datamodel.OrderDirection;

public class OrderBookTests extends BaseTest {

    @BeforeMethod
    public void init() {
        testExchange = new Exchange(smartContractInterfaceMock);
    }

    @Test
    public void checkBuyOneOrder() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.BUY, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getBuySide().length, 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getBuySide()[0].getVolume(), 4);
        Assert.assertEquals(orderBook.getSellSide().length, 0);
    }

    @Test
    public void checkSellOneOrder() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 1);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 4);
        Assert.assertEquals(orderBook.getBuySide().length, 0);
    }

    @Test
    public void checkBuyAllAndPlaceSell() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 4 + 1, 3, OrderDirection.BUY, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 0);
        Assert.assertEquals(orderBook.getBuySide().length, 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getBuySide()[0].getVolume(), 1);
    }

    @Test
    public void checkSellAllAndPlaceBuy() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 4 + 1, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getBuySide().length, 0);
        Assert.assertEquals(orderBook.getSellSide().length, 1);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 1);
    }

    /**
     * Make order and look that orderbook has been created
     */
    @Test
    public void makeSimpleOrderBook() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 1);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 4);
        Assert.assertEquals(orderBook.getBuySide().length, 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getPrice(), 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getVolume(), 2);
    }

    /**
     * create limit 2 limit orders on the one band (row), and look that sum volume is correct for each side
     */
    @Test
    public void moreThenOneOrdersAtOneBand() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 3, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 1);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 7);
        Assert.assertEquals(orderBook.getBuySide().length, 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getPrice(), 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getVolume(), 4);
    }

    @Test
    public void checkEmptyOrderBookAfterFilled() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 2, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 1);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 4);
        Assert.assertEquals(orderBook.getBuySide().length, 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getPrice(), 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getVolume(), 2);
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.BUY, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.SELL, 2, addressClient1, signClient1);
        orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 0);
        Assert.assertEquals(orderBook.getBuySide().length, 0);
    }
}

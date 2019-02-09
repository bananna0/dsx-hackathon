package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.matchingengine.datamodel.OrderBook;

public class OrderBookTests extends BaseTest {

    @Test
    public void checkBuyOneOrder() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide().length, 1);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 4);
        Assert.assertEquals(orderBook.getBuySide().length, 0);
    }
    /**
     * Make order and look that orderbook has been created
     */
    @Test
    public void makeOSimpleOrderBook() {
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
}

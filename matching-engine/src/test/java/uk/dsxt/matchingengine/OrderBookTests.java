package uk.dsxt.matchingengine;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.dsxt.matchingengine.datamodel.OrderBook;

public class OrderBookTests extends BaseTest {

    @Test
    public void makeOSimpleOrderBook() {
        testExchange.openOrder(eurusd, 4, 3, OrderDirection.SELL, 1, addressClient1, signClient1);
        testExchange.openOrder(eurusd, 2, 1, OrderDirection.BUY, 1, addressClient1, signClient1);
        OrderBook orderBook = testExchange.getOrderbook(eurusd);
        Assert.assertEquals(orderBook.getSellSide()[0].getPrice(), 3);
        Assert.assertEquals(orderBook.getSellSide()[0].getVolume(), 4);
        Assert.assertEquals(orderBook.getBuySide()[0].getPrice(), 1);
        Assert.assertEquals(orderBook.getBuySide()[0].getVolume(), 2);
    }
}
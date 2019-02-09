package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.matchingengine.datamodel.Order;
import uk.dsxt.matchingengine.datamodel.PriceLevel;
import uk.dsxt.matchingengine.datamodel.Trade;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

@Log4j2
public class MatchingEngine {
    private String currencyPair;

    private Queue<PriceLevel> sellSide; // top of the orderbook - smbd sell to us
    private Queue<PriceLevel> buySide; // bottom of the orderbook - smbd buy from us

    public MatchingEngine(String currencyPair) {
        currencyPair = currencyPair;
        log.info("Initializing matching engine for {}", currencyPair);

        this.sellSide = new PriorityQueue<>();
        this.buySide = new PriorityQueue<>();
    }

    public OpenOrderResult openOrder(Order newOrder) {
        log.debug("openOrder called: {}", newOrder.toString());

        if (newOrder == null || newOrder.getPrice() <= 0 || newOrder.getAmount() <= 0) {
            return OpenOrderResult.FAILED;
        }

        switch (newOrder.getDirection()) {
            case BUY: {

                for (Iterator<PriceLevel> levelIterator = sellSide.iterator(); levelIterator.hasNext(); ) {
                    PriceLevel level = levelIterator.next();

                    if (newOrder.getPrice() < level.getPrice()) {

                        // TODO put order to buy side

                        return OpenOrderResult.SUCCESS;
                    } else {
                        for (Iterator<Order> orderIterator = level.getOrders().iterator(); orderIterator.hasNext(); ) {
                            Order order = orderIterator.next();

                            long minAmount = Long.min(newOrder.getAmount(), order.getAmount());

                            long orderAmountUpdated = order.getAmount() - minAmount;
                            order.setAmount(orderAmountUpdated);

                            long newOrderAmountUpdated = newOrder.getAmount() - minAmount;
                            newOrder.setAmount(newOrderAmountUpdated);

                            Trade trade = new Trade(minAmount, level.getPrice());

                            if (orderAmountUpdated == 0) {
                                orderIterator.remove();
                            }

                        }
                    }

                }

            }

            case SELL: {

            }
        }

        // TODO Remove it
        return OpenOrderResult.FAILED;
    }

}

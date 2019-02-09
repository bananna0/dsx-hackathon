package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.matchingengine.datamodel.Order;
import uk.dsxt.matchingengine.datamodel.PriceLevel;
import uk.dsxt.matchingengine.datamodel.Trade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    public OpenOrderResult openOrder(Order orderToOpen) {
        log.debug("openOrder called: {}", orderToOpen.toString());

        if (orderToOpen == null || orderToOpen.getPrice() <= 0 || orderToOpen.getAmount() <= 0) {
            return OpenOrderResult.FAILED;
        }

        switch (orderToOpen.getDirection()) {
            case BUY: {

                List<Trade> trades = new ArrayList<>();

                for (Iterator<PriceLevel> levelIterator = sellSide.iterator(); levelIterator.hasNext(); ) {
                    PriceLevel level = levelIterator.next();

                    if (orderToOpen.getPrice() < level.getPrice()) {
                        break;
                    }

                    for (Iterator<Order> orderIterator = level.getOrders().iterator(); orderIterator.hasNext(); ) {
                        Order order = orderIterator.next();

                        long minAmount = Long.min(orderToOpen.getAmount(), order.getAmount());

                        long orderAmountUpdated = order.getAmount() - minAmount;
                        order.setAmount(orderAmountUpdated);

                        long orderToOpenAmountUpdated = orderToOpen.getAmount() - minAmount;
                        orderToOpen.setAmount(orderToOpenAmountUpdated);

                        Trade trade = new Trade(minAmount, level.getPrice());
                        log.debug("Trade created: {}" + trade);
                        trades.add(trade);

                        if (orderAmountUpdated == 0) {
                            orderIterator.remove();
                        }

                        if (orderToOpenAmountUpdated == 0) {
                            break;
                        }
                    }

                    if (level.getOrders().isEmpty()) {
                        levelIterator.remove();
                    }
                }

                if (orderToOpen.getAmount() > 0) {
                    if (!buySide.isEmpty() && buySide.peek().getPrice() >= orderToOpen.getPrice()) {
                        log.error("FATAL EXCEPTION - SERVER STOPPED");
                        return OpenOrderResult.FATAL_ERROR;
                    }
                    buySide.add(new PriceLevel(orderToOpen));
                }

                // TODO Return trades

                return OpenOrderResult.SUCCESS;

            }

            case SELL: {

            }
        }

        // TODO Remove it
        return OpenOrderResult.FAILED;
    }

}

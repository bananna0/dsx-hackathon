package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.matchingengine.datamodel.OpenOrderResultCode;
import uk.dsxt.matchingengine.datamodel.OpenOrderResultInternal;
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

    private long orderNumber;
    private long tradeNumber;

    private Queue<PriceLevel> sellSide; // top of the orderbook - smbd sell to us
    private Queue<PriceLevel> buySide; // bottom of the orderbook - smbd buy from us

    private List<Trade> trades;

    public MatchingEngine(String currencyPair) {
        currencyPair = currencyPair;
        log.info("Initializing matching engine for {}", currencyPair);

        this.orderNumber = 1;
        this.tradeNumber = 1;
        this.trades = new ArrayList<>();

        this.sellSide = new PriorityQueue<>(PriceLevel.sellComparator);
        this.buySide = new PriorityQueue<>(PriceLevel.buyComparator);
    }

    public synchronized OpenOrderResultInternal openOrder(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId, String address, String sign) {
        log.debug("openOrder called. CurrencyPair={}, Amount={}, Price={}, OrderDirection={}", currencyPair, amount, price, direction);

        Order orderToOpen = new Order(orderNumber++, currencyPair, System.currentTimeMillis(), amount, amount, price, direction, clientOrderId, address, sign);

        if (orderToOpen == null || orderToOpen.getPrice() <= 0 || orderToOpen.getAmount() <= 0) {
            return new OpenOrderResultInternal(orderToOpen.getNumber(), OpenOrderResultCode.FAILED, new ArrayList<>());
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

                        Trade trade = new Trade(tradeNumber++, minAmount, level.getPrice());
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

                this.trades.addAll(trades);

                if (orderToOpen.getAmount() > 0) {
                    if (!buySide.isEmpty() && buySide.peek().getPrice() >= orderToOpen.getPrice()) {
                        log.error("FATAL EXCEPTION - SERVER STOPPED");
                        return new OpenOrderResultInternal(orderToOpen.getNumber(), OpenOrderResultCode.FATAL_ERROR, new ArrayList<>());
                    }
                    buySide.add(new PriceLevel(orderToOpen));
                }

                return new OpenOrderResultInternal(orderToOpen.getNumber(), OpenOrderResultCode.SUCCESS, trades);
            }

            case SELL: {

                // TODO Implement

            }
        }

        // TODO Remove it
        return new OpenOrderResultInternal(orderToOpen.getNumber(), OpenOrderResultCode.FAILED, new ArrayList<>());
    }

}

package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.common.datamodel.OpenOrderResultCode;
import uk.dsxt.common.datamodel.OpenOrderResultInternal;
import uk.dsxt.common.datamodel.Order;
import uk.dsxt.common.datamodel.OrderBook;
import uk.dsxt.common.datamodel.OrderBookRow;
import uk.dsxt.common.datamodel.OrderDirection;
import uk.dsxt.common.datamodel.PriceLevel;
import uk.dsxt.common.datamodel.Trade;

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
        log.info("Initializing matching engine for {}", currencyPair);
        this.currencyPair = currencyPair;

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

        List<Trade> trades = new ArrayList<>();

        switch (orderToOpen.getDirection()) {
            case BUY: {
                orderbookMatching(sellSide, buySide, orderToOpen, trades, 1);
                break;
            }
            case SELL: {
                orderbookMatching(buySide, sellSide, orderToOpen, trades, -1);
                break;
            }
        }

        this.trades.addAll(trades);

        return new OpenOrderResultInternal(orderToOpen.getNumber(), OpenOrderResultCode.SUCCESS, trades);
    }

    private void orderbookMatching(Queue<PriceLevel> side, Queue<PriceLevel> otherSide, Order orderToOpen, List<Trade> trades, int AI) {
        for (Iterator<PriceLevel> levelIterator = side.iterator(); levelIterator.hasNext(); ) {
            PriceLevel level = levelIterator.next();

            if ((orderToOpen.getPrice() - level.getPrice()) * AI < 0) {
                break;
            }

            levelMatching(orderToOpen, trades, level);

            if (level.getOrders().isEmpty()) {
                levelIterator.remove();
            }
        }

        if (orderToOpen.getAmount() > 0) {
            boolean orderOpened = false;
            for (PriceLevel level : otherSide) {
                if (orderToOpen.getPrice() == level.getPrice()) {
                    level.getOrders().add(orderToOpen);
                    orderOpened = true;
                    break;
                }
            }

            if (!orderOpened) {
                otherSide.add(new PriceLevel(orderToOpen));
            }
        }
    }

    private void levelMatching(Order orderToOpen, List<Trade> trades, PriceLevel level) {
        for (Iterator<Order> orderIterator = level.getOrders().iterator(); orderIterator.hasNext(); ) {
            Order order = orderIterator.next();

            long minAmount = Long.min(orderToOpen.getAmount(), order.getAmount());
            log.debug("minAmount={}", minAmount);

            long orderAmountUpdated = order.getAmount() - minAmount;
            order.setAmount(orderAmountUpdated);

            long orderToOpenAmountUpdated = orderToOpen.getAmount() - minAmount;
            orderToOpen.setAmount(orderToOpenAmountUpdated);

            Trade trade = new Trade(tradeNumber++, minAmount, level.getPrice());
            log.debug("Trade created: {}", trade);
            trades.add(trade);

            if (orderAmountUpdated == 0) {
                orderIterator.remove();
            }

            if (orderToOpenAmountUpdated == 0) {
                break;
            }
        }
    }

    public synchronized boolean cancelOrder(long clientOrderId, String address, String sign) {
        if (cancelOrderInSide(clientOrderId, address, sellSide)) return true;
        if (cancelOrderInSide(clientOrderId, address, buySide)) return true;
        return false;
    }

    private boolean cancelOrderInSide(long clientOrderId, String address, Queue<PriceLevel> sellSide) {
        for (Iterator<PriceLevel> levelIterator = sellSide.iterator(); levelIterator.hasNext(); ) {
            PriceLevel level = levelIterator.next();
            for (Iterator<Order> orderIterator = level.getOrders().iterator(); orderIterator.hasNext(); ) {
                Order order = orderIterator.next();
                if (order.getClientOrderId() == clientOrderId && order.getAddress().equals(address)) {
                    orderIterator.remove();

                    if (level.getOrders().isEmpty()) {
                        levelIterator.remove();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    public synchronized OrderBook getOrderBook() {
        OrderBookRow[] sellRows = toOrderBookRow(sellSide);
        OrderBookRow[] buyRows = toOrderBookRow(buySide);
        return new OrderBook(sellRows, buyRows);
    }

    private OrderBookRow[] toOrderBookRow(Queue<PriceLevel> side) {
        return side.stream().map(level ->
                new OrderBookRow(level.getPrice(),
                        level.getOrders().stream().map(Order::getAmount).reduce(0L, (v1, v2) -> v1 + v2))).
                toArray(OrderBookRow[]::new);
    }
}

package uk.dsxt.matchingengine;

import uk.dsxt.matchingengine.datamodel.Order;
import uk.dsxt.matchingengine.datamodel.PriceLevel;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MatchingEngine {
    private String currencyPair;

    private Queue<PriceLevel> sellSide; // top of the orderbook - smbd sell to us
    private Queue<PriceLevel> buySide; // bottom of the orderbook - smbd buy from us

    public MatchingEngine(String currencyPair) {
        currencyPair = currencyPair;
        System.out.println("Initializing matching engine for " + currencyPair);

        this.sellSide = new PriorityQueue<>();
        this.buySide = new PriorityQueue<>();
    }

    public void openOrder(Order order) {
        switch (order.getDirection()) {
            case BUY: {

                for (Iterator<PriceLevel> iterator = sellSide.iterator(); iterator.hasNext(); ) {
                    PriceLevel priceLevel = iterator.next();

                }

            }

            case SELL: {

            }
        }
    }

}

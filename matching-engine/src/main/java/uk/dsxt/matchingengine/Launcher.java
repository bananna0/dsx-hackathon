package uk.dsxt.matchingengine;

import uk.dsxt.matchingengine.datamodel.Order;

public class Launcher {

    public static void main(String[] args) {
        MatchingEngine engine = new MatchingEngine("EURUSD");

        engine.openOrder(new Order("EURUSD", 777, 10, OrderDirection.BUY, 1));
    }

}

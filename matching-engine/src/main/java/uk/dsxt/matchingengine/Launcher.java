package uk.dsxt.matchingengine;

public class Launcher {

    public static void main(String[] args) {
        MatchingEngine engine = new MatchingEngine("EURUSD");

        engine.openOrder("EURUSD", 777, 10, OrderDirection.BUY, 1, "address", "sign");
    }

}

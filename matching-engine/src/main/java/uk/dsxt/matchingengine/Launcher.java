package uk.dsxt.matchingengine;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.common.datamodel.OpenOrderResultInternal;
import uk.dsxt.common.datamodel.OrderDirection;

@Log4j2
public class Launcher {

    public static void main(String[] args) {
        MatchingEngine engine = new MatchingEngine("EURUSD");

        OpenOrderResultInternal result1 = engine.openOrder("EURUSD", 777, 10, OrderDirection.BUY, 1, "address", "sign");
        log.debug(result1);
        OpenOrderResultInternal result2 = engine.openOrder("EURUSD", 777, 10, OrderDirection.SELL, 1, "address", "sign");
        log.debug(result2);

    }

}

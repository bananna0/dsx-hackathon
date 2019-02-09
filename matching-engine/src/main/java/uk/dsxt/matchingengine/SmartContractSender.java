package uk.dsxt.matchingengine;

import uk.dsxt.matchingengine.datamodel.OpenOrderResultInternal;
import uk.dsxt.matchingengine.datamodel.Trade;

public class SmartContractSender {

    private SmartContractInterface smartContractInterface;

    public SmartContractSender(SmartContractInterface smartContractInterface) {
        this.smartContractInterface = smartContractInterface;
    }

    public void sendDeals(OpenOrderResultInternal internalResult) {
        for (Trade trade : internalResult.getTrades()) {
            smartContractInterface.dealCreated(trade.getNumber(), null, trade.getAmount(), trade.getPrice(),
                    0, 0, null, null,
                    0, 0, null, null,
                    null);
        }

    }
}

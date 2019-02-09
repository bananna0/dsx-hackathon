package uk.dsxt.matchingengine;

import uk.dsxt.matchingengine.datamodel.OpenOrderResultInternal;
import uk.dsxt.matchingengine.datamodel.Trade;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmartContractSender {

    ExecutorService senderThread = Executors.newSingleThreadExecutor(r -> new Thread(r, "Trades sender"));

    private SmartContractInterface smartContractInterface;

    public SmartContractSender(SmartContractInterface smartContractInterface) {
        this.smartContractInterface = smartContractInterface;
    }

    public void sendDeals(OpenOrderResultInternal internalResult) {
        for (Trade trade : internalResult.getTrades()) {
            senderThread.execute(() -> smartContractInterface.dealCreated(trade.getNumber(), null, trade.getAmount(), trade.getPrice(),
                    0, 0, null, null,
                    0, 0, null, null,
                    null));
        }
    }
}

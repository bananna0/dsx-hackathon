package uk.dsxt.matchingengine.datamodel;

import lombok.Value;

import java.util.List;

@Value
public class OpenOrderResultInternal {
    private long orderNumber;
    private OpenOrderResultCode code;
    private List<Trade> trades;

    public OpenOrderResult toOpenOrderResult() {
        return new OpenOrderResult(orderNumber, code);
    }
}

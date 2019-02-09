package uk.dsxt.matchingengine.datamodel;

import lombok.Value;

@Value
public class OpenOrderResult {
    private long orderNumber;
    private OpenOrderResultCode code;
}

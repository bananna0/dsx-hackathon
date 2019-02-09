package uk.dsxt.common.datamodel;

import lombok.Value;

@Value
public class OpenOrderResult {
    private long orderNumber;
    private OpenOrderResultCode code;
}

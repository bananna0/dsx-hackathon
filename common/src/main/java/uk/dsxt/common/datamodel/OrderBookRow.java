package uk.dsxt.common.datamodel;

import lombok.Value;

@Value
public class OrderBookRow {
    long price;
    long volume;
}

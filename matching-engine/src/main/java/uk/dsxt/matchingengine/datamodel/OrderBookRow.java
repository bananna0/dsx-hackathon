package uk.dsxt.matchingengine.datamodel;

import lombok.Value;

@Value
public class OrderBookRow {
    long price;
    long volume;
}

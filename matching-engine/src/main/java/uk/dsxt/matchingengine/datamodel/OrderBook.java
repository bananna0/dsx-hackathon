package uk.dsxt.matchingengine.datamodel;

import lombok.Value;

@Value
public class OrderBook {
    OrderBookRow[] sellSide;
    OrderBookRow[] buySide;
}

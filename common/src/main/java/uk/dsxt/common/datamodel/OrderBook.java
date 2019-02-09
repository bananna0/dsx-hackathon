package uk.dsxt.common.datamodel;

import lombok.Value;

@Value
public class OrderBook {
    OrderBookRow[] sellSide;
    OrderBookRow[] buySide;
}

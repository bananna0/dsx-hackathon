package uk.dsxt.matchingengine.datamodel;

import lombok.Data;

import java.util.Queue;

@Data
public class PriceLevel {
    private long price;
    private Queue<Order> orders;
}

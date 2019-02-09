package uk.dsxt.matchingengine.datamodel;

import lombok.Data;

import java.util.PriorityQueue;
import java.util.Queue;

@Data
public class PriceLevel {
    private long price;
    private Queue<Order> orders;

    public PriceLevel(Order order) {
        this.price = order.getPrice();
        Queue<Order> orders = new PriorityQueue<>();
        orders.add(order);
        this.orders = orders;
    }
}

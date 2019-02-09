package uk.dsxt.common.datamodel;

import lombok.Data;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

@Data
public class PriceLevel {
    private long price;
    private Queue<Order> orders;

    public static Comparator<PriceLevel> sellComparator = Comparator.comparingLong(PriceLevel::getPrice);
    public static Comparator<PriceLevel> buyComparator = Comparator.comparingLong(PriceLevel::getPrice).reversed();

    public PriceLevel(Order order) {
        this.price = order.getPrice();
        Queue<Order> orders = new PriorityQueue<>(Comparator.comparingLong(Order::getTime));
        orders.add(order);
        this.orders = orders;
    }
}

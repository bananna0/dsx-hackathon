package uk.dsxt.matchingengine.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.dsxt.matchingengine.OrderDirection;

@Data
@AllArgsConstructor
public class Order {
    private String currencyPair;
    private long amount;
    private long initialAmount;
    private long price;
    private OrderDirection direction;
    private long clientOrderId;
    private String address;
    private String sign;

    public Order(String currencyPair, long amount, long price, OrderDirection direction, long clientOrderId) {
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.initialAmount = amount;
        this.price = price;
        this.direction = direction;
        this.clientOrderId = clientOrderId;
        this.address = "";
        this.sign = "";
    }
}

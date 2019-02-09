package uk.dsxt.matchingengine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    String currencyPair;
    long amount;
    long price;
    OrderDirection direction;
    long clientOrderId;
    String address;
    String sign;
}

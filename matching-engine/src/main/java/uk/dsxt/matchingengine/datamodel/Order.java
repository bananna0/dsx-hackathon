package uk.dsxt.matchingengine.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.dsxt.matchingengine.OrderDirection;

@Data
@AllArgsConstructor
public class Order {
    private String currencyPair;
    private long amount;
    private long filledAmount;
    private long price;
    private OrderDirection direction;
    private long clientOrderId;
    private String address;
    private String sign;
}

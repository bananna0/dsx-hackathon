package uk.dsxt.matchingengine.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trade {
    long number;
    long amount;
    long price;
}

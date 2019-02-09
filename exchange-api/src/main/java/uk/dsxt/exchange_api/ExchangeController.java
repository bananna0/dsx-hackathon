package uk.dsxt.exchange_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.dsxt.common.ExchangeInterface;
import uk.dsxt.common.datamodel.OrderDirection;
import uk.dsxt.common.datamodel.OpenOrderResult;

@RestController
@Validated
public class ExchangeController {
    private final ExchangeInterface exchangeInterface;

    @Autowired
    public ExchangeController(ExchangeInterface exchangeInterface) {
        this.exchangeInterface = exchangeInterface;
    }

    @PostMapping(value = "/orders", produces = "application/json")
    @ResponseBody
    public OpenOrderResult openOrder(@RequestParam String currencyPair, @RequestParam long amount, @RequestParam long price,
                                     @RequestParam OrderDirection direction, @RequestParam long clientOrderId,
                                     @RequestParam String address, @RequestParam String sign) {
        return exchangeInterface.openOrder(currencyPair, amount, price, direction, clientOrderId, address, sign);
    }

    @DeleteMapping(value = "/orders/{clientOrderId}", produces = "application/json")
    @ResponseBody
    public boolean cancelOrder(@PathVariable long clientOrderId) {
        return exchangeInterface.cancelOrder(clientOrderId, null, null);
    }
}

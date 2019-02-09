package uk.dsxt.exchange_api;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import uk.dsxt.common.SmartContractInterface;

@RestController
@Validated
@Log4j2
public class SmartContractController {
    private final SmartContractInterface smartContractInterface;

    @Autowired
    public SmartContractController(SmartContractInterface smartContractInterface) {
        this.smartContractInterface = smartContractInterface;
    }

    @PostMapping(value = "/lockMoney", produces = "application/json")
    @ResponseBody
    public boolean lockMoney(@RequestParam long amount, @RequestParam String address, @RequestParam String signature) {
        log.debug("lockMoney, amount={}, address={}, signature={}", amount, address, signature);
        return smartContractInterface.lockMoney(amount, address, signature);
    }
}

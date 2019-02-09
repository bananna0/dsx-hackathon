package uk.dsxt.exchange_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.dsxt.matchingengine.Exchange;
import uk.dsxt.matchingengine.ExchangeInterface;
import uk.dsxt.matchingengine.SmartContractInterface;
import uk.dsxt.matchingengine.SmartContractInterfaceImpl;

@Configuration
public class ExchangeApiConfiguration {
    @Bean
    public ExchangeInterface exchangeInterface() {
        return new Exchange(new SmartContractInterfaceImpl());
    }

    @Bean
    public SmartContractInterface smartContractInterface() {
        return new SmartContractInterfaceImpl();
    }
}

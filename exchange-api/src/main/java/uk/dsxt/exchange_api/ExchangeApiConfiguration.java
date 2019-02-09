package uk.dsxt.exchange_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.dsxt.matchingengine.ExchangeInterface;

@Configuration
public class ExchangeApiConfiguration {
    @Bean
    public ExchangeInterface exchangeInterface() {
        return new MockExchange();
    }
}

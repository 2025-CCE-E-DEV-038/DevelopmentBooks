package com.bnppf.developmentbooks.infrastructure.configuration;

import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    public BasketPriceCalculator basketPriceCalculator() {
        return new BasketPriceCalculator();
    }
}

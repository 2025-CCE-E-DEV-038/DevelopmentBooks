package com.bnppf.developmentbooks.infrastructure.configuration;

import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for wiring Domain services into the Spring Context.
 * <p>
 * This configuration class is used to maintain a strict separation of concerns.
 * The Domain layer remains pure Java, ignorant of the Spring Framework,
 * adhering to Clean Architecture principles.
 * </p>
 */
@Configuration
public class DomainConfiguration {

    @Bean
    public BasketPriceCalculator basketPriceCalculator() {
        return new BasketPriceCalculator();
    }
}

package com.bnppf.developmentbooks.service;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketPriceCalculatorTest {

    @Test
    void should_return_zero_when_basket_is_empty() {
        BasketPriceCalculator calculator = new BasketPriceCalculator();
        ShoppingBasket basket = new ShoppingBasket();

        BigDecimal basketPrice =calculator.computePrice(basket);

        assertThat(basketPrice).isZero();
    }
}

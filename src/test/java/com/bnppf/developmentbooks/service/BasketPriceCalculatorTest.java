package com.bnppf.developmentbooks.service;

import com.bnppf.developmentbooks.domain.model.Book;
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

        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isZero();
    }

    @Test
    void should_return_50_when_basket_has_1_book() {
        BasketPriceCalculator calculator = new BasketPriceCalculator();
        ShoppingBasket basket = new ShoppingBasket();

        basket.addBook(new Book("Clean Code"));
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void should_return_100_when_basket_has_2_copies_of_same_books() {
        BasketPriceCalculator calculator = new BasketPriceCalculator();
        ShoppingBasket basket = new ShoppingBasket();

        basket.addBook(new Book("Clean Code"));
        basket.addBook(new Book("Clean Code"));
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(BigDecimal.valueOf(100));
    }

}

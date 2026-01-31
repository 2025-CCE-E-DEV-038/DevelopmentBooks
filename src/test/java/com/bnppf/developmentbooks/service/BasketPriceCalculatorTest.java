package com.bnppf.developmentbooks.service;

import com.bnppf.developmentbooks.domain.model.Book;
import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketPriceCalculatorTest {

    private final BasketPriceCalculator calculator = new BasketPriceCalculator();

    @Test
    void should_return_zero_when_basket_is_empty() {
        ShoppingBasket basket = new ShoppingBasket();

        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isZero();
    }

    @Test
    void should_return_50_when_basket_has_1_book() {
        ShoppingBasket basket = createBasket("Clean Code");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void should_return_100_when_basket_has_2_copies_of_same_books() {
        ShoppingBasket basket = createBasket("Clean Code", "Clean Code");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(BigDecimal.valueOf(100));
    }

    private ShoppingBasket createBasket(String... titles) {
        ShoppingBasket basket = new ShoppingBasket();
        for (String title : titles) {
            basket.addBook(new Book(title));
        }
        return basket;
    }

    @Test
    void should_return_95_when_basket_has_2_different_books() {
        ShoppingBasket basket = createBasket("Clean Code", "The Clean Coder");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualByComparingTo(BigDecimal.valueOf(95));
    }

    @Test
    void should_return_135_when_basket_has_3_different_books() {
        ShoppingBasket basket = createBasket("Clean Code", "The Clean Coder", "Clean Architecture");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualByComparingTo(BigDecimal.valueOf(135));
    }

    @Test
    void should_return_160_when_basket_has_4_different_books() {
        ShoppingBasket basket = createBasket("Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Test Driven Development by Example");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualByComparingTo(BigDecimal.valueOf(160));
    }

    @Test
    void should_return_187_50_when_basket_has_5_different_books() {
        ShoppingBasket basket = createBasket("Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Test Driven Development by Example",
                "Working Effectively With Legacy Code");

        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualByComparingTo(BigDecimal.valueOf(187.50));
    }

    @Test
    void should_return_185_when_basket_has_4_books_with_3_different_titles() {
        ShoppingBasket basket = createBasket("Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Clean Architecture");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualByComparingTo(BigDecimal.valueOf(185));
    }

}

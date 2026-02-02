package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.Book;
import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketPriceCalculatorTest {

    private final BasketPriceCalculator calculator = new BasketPriceCalculator();

    @Test
    void should_return_zero_when_basket_is_empty() {
        ShoppingBasket basket = new ShoppingBasket();

        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("00.00"));
    }

    @Test
    void should_return_50_when_basket_has_1_book() {
        ShoppingBasket basket = createBasket("Clean Code");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("50.00"));
    }

    @Test
    void should_return_100_when_basket_has_2_copies_of_same_books() {
        ShoppingBasket basket = createBasket("Clean Code", "Clean Code");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("100.00"));
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

        assertThat(basketPrice).isEqualTo(new BigDecimal("95.00"));
    }

    @Test
    void should_return_135_when_basket_has_3_different_books() {
        ShoppingBasket basket = createBasket("Clean Code", "The Clean Coder", "Clean Architecture");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("135.00"));
    }

    @Test
    void should_return_160_when_basket_has_4_different_books() {
        ShoppingBasket basket = createBasket("Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Test Driven Development by Example");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("160.00"));
    }

    @Test
    void should_return_187_50_when_basket_has_5_different_books() {
        ShoppingBasket basket = createBasket("Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Test Driven Development by Example",
                "Working Effectively With Legacy Code");

        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("187.50"));
    }

    @Test
    void should_return_185_when_basket_has_4_books_with_3_different_titles() {
        ShoppingBasket basket = createBasket("Clean Code",
                "The Clean Coder",
                "Clean Architecture",
                "Clean Architecture");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("185.00"));
    }

    @Test
    void should_optimize_discount_by_favoring_2_sets_of_4_over_set_of_5_and_3() {
        ShoppingBasket basket = createBasket("Clean Code",
                "Clean Code",
                "The Clean Coder",
                "The Clean Coder",
                "Clean Architecture",
                "Clean Architecture",
                "Test Driven Development by Example",
                "Working Effectively With Legacy Code");
        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("320.00"));
    }
    @Test
    void should_return_a_decimal_rounded_to_2_decimal_places() {
        ShoppingBasket basket = createBasket("Clean Code");

        BigDecimal basketPrice = calculator.computePrice(basket);

        assertThat(basketPrice).isEqualTo(new BigDecimal("50.00"));
        assertThat(basketPrice.scale()).isEqualTo(2);
    }

    @Test
    void should_handle_large_basket_optimized_grouping() {
        ShoppingBasket basket = new ShoppingBasket();
        for (int i = 0; i < 10; i++) {
            basket.addBook(new Book("Clean Code"));
            basket.addBook(new Book("The Clean Coder"));
            basket.addBook(new Book("Clean Architecture"));
            basket.addBook(new Book("Test Driven Development by Example"));
            basket.addBook(new Book("Working Effectively with Legacy Code"));
        }
        BigDecimal price = calculator.computePrice(basket);

        assertThat(price).isEqualTo("1875.00");
    }
}

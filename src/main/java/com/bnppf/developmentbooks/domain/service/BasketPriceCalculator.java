package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;

import java.math.BigDecimal;

public class BasketPriceCalculator {

    private static final BigDecimal BOOK_PRICE = BigDecimal.valueOf(50);
    private static final BigDecimal DISCOUNT_MULTIPLIER_2_BOOKS = BigDecimal.valueOf(0.95);
    private static final BigDecimal DISCOUNT_MULTIPLIER_3_BOOKS = BigDecimal.valueOf(0.90);

    public BigDecimal computePrice(ShoppingBasket basket) {
        int numberOfBooks = basket.getBooks().size();
        long numberOfDifferentBooks = basket.getBooks().stream().distinct().count();

        if (numberOfBooks == numberOfDifferentBooks && numberOfDifferentBooks == 2) {
            return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks)).multiply(DISCOUNT_MULTIPLIER_2_BOOKS);
        }
        if (numberOfBooks == numberOfDifferentBooks && numberOfDifferentBooks == 3) {
            return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks)).multiply(DISCOUNT_MULTIPLIER_3_BOOKS);
        }
        if (numberOfBooks == numberOfDifferentBooks && numberOfDifferentBooks == 4) {
            return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks)).multiply(BigDecimal.valueOf(0.80));
        }
        return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks));
    }
}

package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;

import java.math.BigDecimal;

public class BasketPriceCalculator {

    private static final BigDecimal BOOK_PRICE = BigDecimal.valueOf(50);

    public BigDecimal computePrice(ShoppingBasket basket) {
        int numberOfBooks = basket.getNumberOfBooks();
        return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks));
    }
}

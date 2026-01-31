package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;

import java.math.BigDecimal;
import java.util.Map;

public class BasketPriceCalculator {

    private static final BigDecimal BOOK_PRICE = BigDecimal.valueOf(50);
    private static final Map<Integer, BigDecimal> DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS = Map.of(
            2, BigDecimal.valueOf(0.95),
            3, BigDecimal.valueOf(0.90),
            4, BigDecimal.valueOf(0.80));

    public BigDecimal computePrice(ShoppingBasket basket) {
        int numberOfBooks = basket.getBooks().size();
        int numberOfDifferentBooks = (int) basket.getBooks().stream().distinct().count();

        if (numberOfBooks == numberOfDifferentBooks && DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.containsKey(numberOfDifferentBooks)) {
            BigDecimal discountMultiplier = DISCOUNT_MULTIPLIER_BY_NUMBER_OF_BOOKS.get(numberOfDifferentBooks);
            return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks)).multiply(discountMultiplier);
        }
        return BOOK_PRICE.multiply(BigDecimal.valueOf(numberOfBooks));
    }
}

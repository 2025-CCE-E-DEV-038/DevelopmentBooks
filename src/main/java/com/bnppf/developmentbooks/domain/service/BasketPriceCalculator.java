package com.bnppf.developmentbooks.domain.service;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;

import java.math.BigDecimal;

public class BasketPriceCalculator {

    public BigDecimal computePrice(ShoppingBasket basket) {
        return BigDecimal.ONE.negate();
    }
}

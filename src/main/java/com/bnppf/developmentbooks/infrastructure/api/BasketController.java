package com.bnppf.developmentbooks.infrastructure.api;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import com.bnppf.developmentbooks.infrastructure.api.dto.ShoppingBasketRequest;
import com.bnppf.developmentbooks.infrastructure.api.mapper.ShoppingBasketMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketPriceCalculator basketPriceCalculator;
    private final ShoppingBasketMapper shoppingBasketMapper;

    public BasketController(BasketPriceCalculator basketPriceCalculator,
                            ShoppingBasketMapper shoppingBasketMapper) {
        this.basketPriceCalculator = basketPriceCalculator;
        this.shoppingBasketMapper = shoppingBasketMapper;
    }

    @PostMapping("/price")
    public ResponseEntity<BigDecimal> calculatePrice(@RequestBody ShoppingBasketRequest basketRequest) {
        if (basketRequest.books() == null) {
            return ResponseEntity.badRequest().build();
        }
        ShoppingBasket basket = shoppingBasketMapper.toDomain(basketRequest.books());

        BigDecimal basketPrice = basketPriceCalculator.computePrice(basket);
        return ResponseEntity.ok(basketPrice);
    }
}

package com.bnppf.developmentbooks.infrastructure.api;

import com.bnppf.developmentbooks.domain.model.Book;
import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketPriceCalculator basketPriceCalculator;

    public BasketController(BasketPriceCalculator basketPriceCalculator) {
        this.basketPriceCalculator = basketPriceCalculator;
    }

    @PostMapping("/price")
    public ResponseEntity<BigDecimal> calculatePrice(@RequestBody List<String> bookTitles) {
        ShoppingBasket basket = new ShoppingBasket();

        if (bookTitles != null) {
            for (String title : bookTitles) {
                basket.addBook(new Book(title));
            }
        }

        BigDecimal basketPrice = basketPriceCalculator.computePrice(basket);
        return ResponseEntity.ok(basketPrice);
    }
}

package com.bnppf.developmentbooks.infrastructure.api;

import com.bnppf.developmentbooks.domain.model.ShoppingBasket;
import com.bnppf.developmentbooks.domain.service.BasketPriceCalculator;
import com.bnppf.developmentbooks.infrastructure.api.dto.ShoppingBasketRequest;
import com.bnppf.developmentbooks.infrastructure.api.mapper.ShoppingBasketMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * REST Controller implementing the entry point for Basket service.
 * <p>
 * Follows Clean Architecture pattern by delegating business logic
 * to the {@link BasketPriceCalculator} and mapping inputs via {@link ShoppingBasketMapper}.
 * </p>
 */
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

    @Operation(summary = "Calculate Basket Price", description = "Returns the optimized lowest price for the provided list of books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid JSON format or missing body")
    })
    @PostMapping("/price")
    public ResponseEntity<BigDecimal> calculatePrice(@RequestBody @Valid ShoppingBasketRequest basketRequest) {
        if (basketRequest.books() == null) {
            return ResponseEntity.badRequest().build();
        }
        ShoppingBasket basket = shoppingBasketMapper.toDomain(basketRequest.books());

        BigDecimal basketPrice = basketPriceCalculator.computePrice(basket);
        return ResponseEntity.ok(basketPrice);
    }
}

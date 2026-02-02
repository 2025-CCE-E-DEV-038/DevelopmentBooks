package com.bnppf.developmentbooks.infrastructure.api.dto;

import com.bnppf.developmentbooks.infrastructure.api.validation.ValidationErrors;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ShoppingBasketRequest(@NotNull(message = ValidationErrors.SHOPPING_BASKET_NULL_LIST_ERROR)
                                    @NotEmpty(message = ValidationErrors.SHOPPING_BASKET_EMPTY_LIST_ERROR)
                                    List<String> books) {}

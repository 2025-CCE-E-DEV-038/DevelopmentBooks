package com.bnppf.developmentbooks.infrastructure.api.dto;

import com.bnppf.developmentbooks.infrastructure.api.validation.ValidationErrors;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ShoppingBasketRequest(@NotNull(message = ValidationErrors.SHOPPING_BASKET_NULL_LIST_ERROR)
                                    @Size(min = 1, message = ValidationErrors.SHOPPING_BASKET_EMPTY_LIST_ERROR)
                                    List<String> books) {}

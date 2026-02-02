package com.bnppf.developmentbooks.infrastructure.api.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ShoppingBasketRequest(@NotNull(message = "Book list cannot be null")
                                    List<String> books) {}

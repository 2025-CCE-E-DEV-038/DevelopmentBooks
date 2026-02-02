package com.bnppf.developmentbooks.infrastructure.api.validation;

public final class ValidationErrors {

    private ValidationErrors() {}

    // Request problem details
    public static final String REQUEST_VALIDATION_TITLE = "Invalid Request";
    public static final String REQUEST_VALIDATION_DETAIL = "Validation failed for request parameters";
    public static final String REQUEST_VALIDATION_TIME_PROPERTY = "timestamp";
    public static final String REQUEST_VALIDATION_FIELD_ERROR_PREFIX = "error_";
    // Field Errors
    public static final String SHOPPING_BASKET_NULL_LIST_ERROR= "Book list cannot be null";
}
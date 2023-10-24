package com.ingress.msrating.model.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionConstants {

    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),
    METHOD_ARGUMENT_NOT_VALID("METHOD_ARGUMENT_NOT_VALID", "Method argument not valid"),
    RATING_NOT_FOUND("RATING_NOT_FOUND", "Rating not found with product-id: %s"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Invalid request parameter(s)"),
    METHOD_NOT_ALLOWED_EXCEPTION("METHOD_NOT_ALLOWED_EXCEPTION", "Method not allowed");

    private final String code;
    private final String message;
}
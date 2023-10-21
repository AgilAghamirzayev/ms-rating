package com.ingress.msrating.exception;

import com.ingress.msrating.model.constants.ExceptionConstants;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String code;

    public ResourceNotFoundException(ExceptionConstants exceptionConstants, Long id) {
        super(exceptionConstants.getMessage().concat(String.valueOf(id)));
        this.code = exceptionConstants.getCode();
    }
}

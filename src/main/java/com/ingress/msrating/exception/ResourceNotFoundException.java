package com.ingress.msrating.exception;

import com.ingress.msrating.model.constants.ExceptionConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String code;
    private final Integer httpStatus;

    public ResourceNotFoundException(ExceptionConstants exceptionConstants) {
        super(exceptionConstants.getMessage());
        this.code = exceptionConstants.getCode();
        this.httpStatus = HttpStatus.NOT_FOUND.value();
    }

    public ResourceNotFoundException(ExceptionConstants exceptionConstants, String customMessage) {
        super(exceptionConstants.getMessage().concat(customMessage));
        this.code = exceptionConstants.getCode();
        this.httpStatus = HttpStatus.NOT_FOUND.value();
    }
}

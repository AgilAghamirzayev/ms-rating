package com.ingress.msrating.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ingress.msrating.model.constants.ExceptionConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String code;
    private String message;
    private List<Map<String, String>> validationErrors;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionResponse(ExceptionConstants exceptionConstants) {
        this.code = exceptionConstants.getCode();
        this.message = exceptionConstants.getMessage();
    }
}

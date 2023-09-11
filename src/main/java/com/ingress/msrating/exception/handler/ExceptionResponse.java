package com.ingress.msrating.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ingress.msrating.model.constants.ExceptionConstants;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private String code;
    private String message;
    private List<Map<String, String>> messages;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionResponse(ExceptionConstants exceptionConstants) {
        this.code = exceptionConstants.getCode();
        this.message = exceptionConstants.getMessage();
    }

    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(String code, List<Map<String, String>> messages) {
        this.code = code;
        this.messages = messages;
    }
}

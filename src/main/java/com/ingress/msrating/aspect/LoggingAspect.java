package com.ingress.msrating.aspect;

import static com.ingress.msrating.model.constants.LogColorsConstants.ANSI_GREEN;
import static com.ingress.msrating.model.constants.LogColorsConstants.ANSI_RED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingress.msrating.annotation.LogIgnore;

import java.lang.reflect.Parameter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper;

    @Around(value = "within(@com.ingress.msrating.annotation.Log *)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        var parameters = buildParameters(signature, joinPoint.getArgs());

        logEvent("start", signature, parameters, ANSI_GREEN);

        Object response;
        try {
            response = joinPoint.proceed();
        } catch (Throwable throwable) {
            logEvent("error", signature, parameters, ANSI_RED);
            throw throwable;
        }

        logEndAction(signature, response);

        return response;
    }

    private StringBuilder buildParameters(MethodSignature signature, Object[] args) {
        var builder = new StringBuilder();
        var parameters = signature.getMethod().getParameters();

        for (int i = 0; i < parameters.length; i++) {
            var currentParameter = parameters[i];
            if (currentParameter.isAnnotationPresent(LogIgnore.class)) {
                continue;
            }

            var paramName = currentParameter.getName();
            var paramValue = writeObjectAsString(args[i], currentParameter);
            var paramLog = String.format(" %s: %s", paramName, paramValue);

            builder.append(paramLog);
        }

        return builder;
    }

    private String writeObjectAsString(Object obj, Parameter parameter) {
        try {
            var className = parameter.getType().getSimpleName();
            var classValue = objectMapper.writeValueAsString(obj)
                    .replaceAll("[{}\"]", "")
                    .replace(",", ", ");

            return String.format("%s{%s}", className, classValue);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }

    private void logEvent(String eventName, MethodSignature signature, StringBuilder parameters, String color) {
        log.info("{}ActionLog.{}.{}{}", color, signature.getName(), eventName, parameters);
    }

    private void logEndAction(MethodSignature signature, Object response) {
        if (void.class.equals(signature.getReturnType())) {
            log.info("{}ActionLog.{}.end", ANSI_GREEN, signature.getName());
        } else {
            log.info("{}ActionLog.{}.end {}", ANSI_GREEN, signature.getName(), response);
        }
    }
}

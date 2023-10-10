package com.ingress.msrating.model.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EnvironmentConstants {

    @Value("${rabbitmq.queue.name}")
    private String ratingQueue;
}

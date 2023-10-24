package com.ingress.msrating.model.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnvironmentConstants {

    @Value("${rabbitmq.queue.name}")
    private String ratingQueue;
}

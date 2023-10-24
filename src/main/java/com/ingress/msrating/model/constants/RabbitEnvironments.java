package com.ingress.msrating.model.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.queue")
public record RabbitEnvironments(String name) {

}

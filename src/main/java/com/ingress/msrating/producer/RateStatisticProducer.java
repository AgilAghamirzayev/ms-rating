package com.ingress.msrating.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class RateStatisticProducer {

    private final AmqpTemplate template;

    public <T> void publishMessage(String queue, T data) {
        template.convertAndSend(queue, data);
        log.info("publishMessage.END.SUCCESS");
    }

}

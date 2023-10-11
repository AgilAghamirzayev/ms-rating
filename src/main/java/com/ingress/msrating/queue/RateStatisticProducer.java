package com.ingress.msrating.queue;

import com.ingress.msrating.annotation.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Log
@Component
@RequiredArgsConstructor
public class RateStatisticProducer {

    private final AmqpTemplate template;

    public <T> void publishMessage(String queue, T data) {
        template.convertAndSend(queue, data);
    }

}

package com.ingress.msrating.queue

import com.ingress.msrating.model.client.RatingStatistic
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.amqp.core.AmqpTemplate
import spock.lang.Specification

class RateStatisticQueueTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    AmqpTemplate amqpTemplate
    RateStatisticQueue rateStatisticQueue

    def setup() {
        amqpTemplate = Mock()
        rateStatisticQueue = new RateStatisticQueue(amqpTemplate)
    }

    def "test publishMessage"() {
        given:
        def queue = random.nextObject(String)
        def ratingStatistic = random.nextObject(RatingStatistic)

        when:
        rateStatisticQueue.publishMessage(queue, ratingStatistic)

        then:
        1 * amqpTemplate.convertAndSend(queue, ratingStatistic)
    }
}

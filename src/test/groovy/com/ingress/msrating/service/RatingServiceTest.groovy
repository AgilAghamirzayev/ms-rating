package com.ingress.msrating.service

import com.ingress.msrating.dao.entity.RatingEntity
import com.ingress.msrating.dao.repository.RatingRepository
import com.ingress.msrating.exception.ResourceNotFoundException
import com.ingress.msrating.model.client.RatingStatistic
import com.ingress.msrating.model.constants.RabbitEnvironments
import com.ingress.msrating.model.request.RatingRequest
import com.ingress.msrating.queue.RateStatisticQueue
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.ingress.msrating.model.constants.ExceptionConstants.RATING_NOT_FOUND

class RatingServiceTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    RatingService ratingService
    RatingRepository ratingRepository
    RabbitEnvironments rabbitEnvironments
    RateStatisticQueue rateStatisticProducer

    def setup() {
        ratingRepository = Mock()
        rateStatisticProducer = Mock()
        rabbitEnvironments = new RabbitEnvironments("rate_statistics_q")
        ratingService = new RatingService(ratingRepository, rateStatisticProducer, rabbitEnvironments)
    }

    def "rate product successfully"() {
        given:
        def userId = random.nextLong()
        def ratingRequest = new RatingRequest(random.nextLong(), random.nextInt())
        def ratingStatistic = random.nextObject(RatingStatistic)
        def entity = RatingEntity.builder()
                .userId(userId)
                .productId(ratingRequest.productId())
                .rate(ratingRequest.rate())
                .build()

        when:
        ratingService.rate(userId, ratingRequest)

        then:
        1 * ratingRepository.save(entity)
        1 * ratingRepository.getRatingStatistic(ratingRequest.productId()) >> Optional.of(ratingStatistic)
        1 * rateStatisticProducer.publishMessage(rabbitEnvironments.name(), ratingStatistic)
    }

    def "rate product throw ResourceNotFoundException"() {
        given:
        def userId = random.nextLong()
        def ratingRequest = new RatingRequest(random.nextLong(), random.nextInt())
        def ratingStatistic = random.nextObject(RatingStatistic)
        def entity = RatingEntity.builder()
                .userId(userId)
                .productId(ratingRequest.productId())
                .rate(ratingRequest.rate())
                .build()

        when:
        ratingService.rate(userId, ratingRequest)

        then:
        1 * ratingRepository.save(entity)
        1 * ratingRepository.getRatingStatistic(ratingRequest.productId()) >> Optional.empty()
        0 * rateStatisticProducer.publishMessage(rabbitEnvironments.name(), ratingStatistic)

        ResourceNotFoundException exception = thrown()
        exception.getCode() == RATING_NOT_FOUND.getCode()
    }
}

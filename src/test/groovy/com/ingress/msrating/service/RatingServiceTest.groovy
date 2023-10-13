package com.ingress.msrating.service

import com.ingress.msrating.dao.entity.RatingEntity
import com.ingress.msrating.dao.repository.RatingRepository
import com.ingress.msrating.exception.ResourceNotFoundException
import com.ingress.msrating.model.client.RatingStatistic
import com.ingress.msrating.model.constants.EnvironmentConstants
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
    EnvironmentConstants environmentConstants
    RateStatisticQueue rateStatisticProducer

    def setup() {
        ratingRepository = Mock()
        rateStatisticProducer = Mock()
        environmentConstants = new EnvironmentConstants()
        environmentConstants.setRatingQueue("rate_statistics_q")
        ratingService = new RatingService(ratingRepository, environmentConstants, rateStatisticProducer)
    }

    def "rate product successfully"() {
        given:
        def userId = 2L
        def ratingRequest = new RatingRequest(1L, 4)
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
        1 * rateStatisticProducer.publishMessage(environmentConstants.ratingQueue, ratingStatistic)
    }

    def "rate product throw ResourceNotFoundException"() {
        given:
        def userId = 2L
        def ratingRequest = new RatingRequest(1L, 4)
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
        0 * rateStatisticProducer.publishMessage(environmentConstants.ratingQueue, ratingStatistic)

        ResourceNotFoundException exception = thrown()
        exception.getCode() == RATING_NOT_FOUND.getCode()
    }
}

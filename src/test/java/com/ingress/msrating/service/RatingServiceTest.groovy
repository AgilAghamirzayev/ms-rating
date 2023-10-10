package com.ingress.msrating.service

import com.ingress.msrating.dao.entity.RatingEntity
import com.ingress.msrating.dao.repository.RatingRepository
import com.ingress.msrating.model.client.RatingStatistic
import com.ingress.msrating.model.constants.EnvironmentConstants
import com.ingress.msrating.model.request.RatingRequest
import com.ingress.msrating.queue.RateStatisticProducer
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class RatingServiceTest extends Specification {
    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    RateStatisticProducer rateStatisticProducer;
    EnvironmentConstants environmentConstants;
    RatingRepository ratingRepository
    RatingService ratingService

    def setup() {
        ratingRepository = Mock()
        environmentConstants = Mock()
        rateStatisticProducer = Mock()
        ratingService = new RatingService(ratingRepository, environmentConstants, rateStatisticProducer)
    }

    def "Rate product with valid request"() {
        given:
        def userId = random.nextObject(Long)
        def ratingRequest = random.nextObject(RatingRequest)
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
        1 * ratingRepository.getRatingStatistic(ratingRequest.productId()) >> ratingStatistic
        1 * rateStatisticProducer.publishMessage(environmentConstants.ratingQueue, ratingStatistic)
    }

}

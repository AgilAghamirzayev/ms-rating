package com.ingress.msrating.mapper

import com.ingress.msrating.model.request.RatingRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static com.ingress.msrating.mapper.RatingMapper.RATING_MAPPER

class RatingMapperTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "map to rating entity"() {
        given:
        def userId = random.nextLong()
        def ratingRequest = new RatingRequest(random.nextLong(), random.nextInt())

        when:
        def ratingEntity = RATING_MAPPER.toRatingEntity(userId, ratingRequest)

        then:
        ratingEntity.userId == userId
        ratingEntity.productId == ratingRequest.productId
        ratingEntity.rate == ratingRequest.rate
    }
}

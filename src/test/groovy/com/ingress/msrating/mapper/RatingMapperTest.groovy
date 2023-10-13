package com.ingress.msrating.mapper

import com.ingress.msrating.model.request.RatingRequest
import spock.lang.Specification

class RatingMapperTest extends Specification {

    def "map to rating entity"() {
        given:
        def userId = 2L
        def ratingRequest = new RatingRequest(1L, 4)

        when:
        def ratingEntity = RatingMapper.RATING_MAPPER.toRatingEntity(userId, ratingRequest)

        then:
        ratingEntity.getUserId() == userId
        ratingEntity.getProductId() == ratingRequest.productId()
        ratingEntity.getRate() == ratingRequest.rate()
    }
}

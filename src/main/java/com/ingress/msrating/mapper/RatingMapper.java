package com.ingress.msrating.mapper;

import com.ingress.msrating.dao.entity.RatingEntity;
import com.ingress.msrating.model.request.RatingRequest;

public enum RatingMapper {
    RATING_MAPPER;

    public RatingEntity toRatingEntity(Long userId, RatingRequest ratingRequest) {
        return RatingEntity.builder()
                .userId(userId)
                .productId(ratingRequest.productId())
                .rate(ratingRequest.rate())
                .build();
    }
}

package com.ingress.msrating.service;

import static com.ingress.msrating.mapper.RatingMapper.RATING_MAPPER;
import static com.ingress.msrating.model.constants.ExceptionConstants.RATING_NOT_FOUND;

import com.ingress.msrating.annotation.Log;
import com.ingress.msrating.dao.entity.RatingEntity;
import com.ingress.msrating.exception.ResourceNotFoundException;
import com.ingress.msrating.model.client.RatingStatistic;
import com.ingress.msrating.model.constants.EnvironmentConstants;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.queue.RateStatisticProducer;
import com.ingress.msrating.dao.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final EnvironmentConstants environmentConstants;
    private final RateStatisticProducer rateStatisticProducer;

    public void rate(Long userId, RatingRequest ratingRequest) {
        var rating = RATING_MAPPER.toRatingEntity(userId, ratingRequest);
        var savedRating = ratingRepository.save(rating);
        var rateStatistic = getRateStatistic(savedRating.getProductId());
        rateStatisticProducer.publishMessage(environmentConstants.getRatingQueue(), rateStatistic);
    }

    private RatingStatistic getRateStatistic(Long productId) {
        return ratingRepository.getRatingStatistic(productId)
                .orElseThrow(() -> new ResourceNotFoundException(RATING_NOT_FOUND, productId));
    }
}

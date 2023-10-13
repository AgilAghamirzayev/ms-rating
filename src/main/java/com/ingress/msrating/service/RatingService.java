package com.ingress.msrating.service;

import static com.ingress.msrating.mapper.RatingMapper.RATING_MAPPER;
import static com.ingress.msrating.model.constants.ExceptionConstants.RATING_NOT_FOUND;

import com.ingress.msrating.annotation.Log;
import com.ingress.msrating.dao.repository.RatingRepository;
import com.ingress.msrating.exception.ResourceNotFoundException;
import com.ingress.msrating.model.client.RatingStatistic;
import com.ingress.msrating.model.constants.EnvironmentConstants;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.queue.RateStatisticQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final EnvironmentConstants environmentConstants;
    private final RateStatisticQueue rateStatisticQueue;

    public void rate(Long userId, RatingRequest ratingRequest) {
        var rating = RATING_MAPPER.toRatingEntity(userId, ratingRequest);
        ratingRepository.save(rating);
        var rateStatistic = getRateStatistic(rating.getProductId());
        rateStatisticQueue.publishMessage(environmentConstants.getRatingQueue(), rateStatistic);
    }

    private RatingStatistic getRateStatistic(Long productId) {
        return ratingRepository.getRatingStatistic(productId)
                .orElseThrow(() -> new ResourceNotFoundException(RATING_NOT_FOUND, productId));
    }
}

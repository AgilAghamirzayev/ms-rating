package com.ingress.msrating.service;

import com.ingress.msrating.entity.Rating;
import com.ingress.msrating.model.client.RatingStatistic;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public void rate(Long userId, RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .userId(userId)
                .productId(ratingRequest.productId())
                .rate(ratingRequest.rate())
                .build();

        ratingRepository.save(rating);
    }

    public RatingStatistic getRateStatistic(Long productId) {
        RatingStatistic ratingStatistic = ratingRepository.getRatingStatistic(productId);
        log.info("Rating: {}", ratingStatistic);
        return ratingStatistic;
    }
}

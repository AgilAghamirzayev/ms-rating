package com.ingress.msrating.service;

import static com.ingress.msrating.model.constants.ExceptionConstants.RATING_NOT_FOUND;

import com.ingress.msrating.entity.Rating;
import com.ingress.msrating.exception.ResourceNotFoundException;
import com.ingress.msrating.model.client.RatingStatistic;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.producer.RateStatisticProducer;
import com.ingress.msrating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RateStatisticProducer rateStatisticProducer;
    @Value("${rabbitmq.queue.name}")
    private String ratingQueue;

    public void rate(Long userId, RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .userId(userId)
                .productId(ratingRequest.productId())
                .rate(ratingRequest.rate())
                .build();

        Rating savedRating = ratingRepository.save(rating);
        log.info("Saved rating id: {}", savedRating.getId());

        RatingStatistic rateStatistic = getRateStatistic(savedRating.getProductId());
        rateStatisticProducer.publishMessage(ratingQueue, rateStatistic);
    }

    public RatingStatistic getRateStatistic(Long productId) {
        RatingStatistic ratingStatistic = ratingRepository.getRatingStatistic(productId).orElseThrow(() ->
                new ResourceNotFoundException(RATING_NOT_FOUND, String.format("with product-id: %s", productId)));
        log.info("Rating: {}", ratingStatistic);
        return ratingStatistic;
    }
}

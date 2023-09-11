package com.ingress.msrating.service;

import com.ingress.msrating.entity.Rating;
import com.ingress.msrating.model.client.RatingStatistic;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.producer.RateStatisticProducer;
import com.ingress.msrating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class RatingService {
    @Value("${rabbitmq.queue.name}")
    private String ratingQueue;

    private final RatingRepository ratingRepository;
    private final RateStatisticProducer rateStatisticProducer;

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
        RatingStatistic ratingStatistic = ratingRepository.getRatingStatistic(productId)
                .orElseThrow(() -> new RuntimeException("Could not find rating"));
        log.info("Rating: {}", ratingStatistic);
        return ratingStatistic;
    }
}

package com.ingress.msrating.model.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingStatistic {
    private Long productId;
    private Double averageRate;
    private Long totalRateCount;
    private Long oneStarRateCount;
    private Long twoStarRateCount;
    private Long threeStarRateCount;
    private Long fourStarRateCount;
    private Long fiveStarRateCount;
}

package com.ingress.msrating.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record RatingRequest(Long productId, @Max(5) @Min(1) Integer rate) {

}

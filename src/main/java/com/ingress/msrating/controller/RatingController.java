package com.ingress.msrating.controller;

import com.ingress.msrating.model.client.RatingStatistic;
import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/ratings")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void rate(@RequestHeader("user-id") Long userId,
                     @RequestBody RatingRequest ratingRequest) {

        ratingService.rate(userId, ratingRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RatingStatistic test() {
       return ratingService.getRateStatistic(1L);
    }

}

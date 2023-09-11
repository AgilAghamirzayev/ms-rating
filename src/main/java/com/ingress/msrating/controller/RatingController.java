package com.ingress.msrating.controller;

import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}

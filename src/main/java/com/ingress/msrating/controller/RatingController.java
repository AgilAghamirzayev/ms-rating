package com.ingress.msrating.controller;

import static com.ingress.msrating.model.constants.HeaderConstants.USER_ID;

import com.ingress.msrating.model.request.RatingRequest;
import com.ingress.msrating.service.RatingService;
import jakarta.validation.Valid;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rate(@RequestHeader(USER_ID) Long userId,
                     @RequestBody @Valid RatingRequest ratingRequest) {

        ratingService.rate(userId, ratingRequest);
    }

}

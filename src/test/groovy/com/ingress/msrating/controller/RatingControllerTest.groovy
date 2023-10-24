package com.ingress.msrating.controller

import com.ingress.msrating.exception.handler.CustomExceptionHandler
import com.ingress.msrating.model.constants.HeaderConstants
import com.ingress.msrating.model.request.RatingRequest
import com.ingress.msrating.service.RatingService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static com.ingress.msrating.model.constants.HeaderConstants.USER_ID
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class RatingControllerTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    MockMvc mockMvc
    RatingService ratingService

    def setup() {
        ratingService = Mock()
        def ratingController = new RatingController(ratingService)
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build()
    }

    def "test rate product success case"() {
        given:
        def userId = random.nextLong()
        def url = "/v1/ratings"
        def ratingRequest = new RatingRequest(2L, 3)
        def jsonRequest = """
                                    {
                                        "productId": 2,
                                        "rate": 3   
                                    }
                                 """

        when:
        def result = mockMvc.perform(post(url)
                .header(USER_ID, userId)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn()


        then:
        1 * ratingService.rate(userId, ratingRequest)
        result.response.status == NO_CONTENT.value()
    }

    def "test rate product error case"() {
        given:
        def userId = random.nextLong()
        def url = "/v1/ratings"
        def ratingRequest = new RatingRequest(2L, 8)
        def jsonRequest = """
                                    {
                                        "productId": 2,
                                        "rate": 8   
                                    }
                                 """

        def errorResponse = '{"code":"VALIDATION_ERROR",' +
                '"validationErrors":[{"field":"rate","message":"must be less than or equal to 5"}]}'

        when:
        def result = mockMvc.perform(post(url)
                .header(USER_ID, userId)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn()

        then:
        0 * ratingService.rate(userId, ratingRequest)
        result.response.status == BAD_REQUEST.value()
        result.response.contentAsString == errorResponse
    }
}

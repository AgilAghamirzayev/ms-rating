package com.ingress.msrating.controller

import com.ingress.msrating.exception.handler.CustomExceptionHandler
import com.ingress.msrating.model.request.RatingRequest
import com.ingress.msrating.service.RatingService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class RatingControllerTest extends Specification {

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
        def userId = 1L
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
                .header("User-Id", userId)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn()


        then:
        1 * ratingService.rate(userId, ratingRequest)
        result.response.status == NO_CONTENT.value()
    }

    def "test rate product error case"() {
        given:
        def userId = 1L
        def url = "/v1/ratings"
        def ratingRequest = new RatingRequest(2L, 8)
        def jsonRequest = """
                                    {
                                        "productId": 2,
                                        "rate": 8   
                                    }
                                 """

        when:
        def result = mockMvc.perform(post(url)
                .header("User-Id", userId)
                .contentType(APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn()

        then:
        0 * ratingService.rate(userId, ratingRequest)
        result.response.status == BAD_REQUEST.value()
    }
}

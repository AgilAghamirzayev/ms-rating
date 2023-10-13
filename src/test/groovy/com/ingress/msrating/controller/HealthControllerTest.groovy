package com.ingress.msrating.controller

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class HealthControllerTest extends Specification {

    MockMvc mockMvc

    def setup() {
        def healthController = new HealthController()
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).build()
    }

    def "test check health"() {
        given:
        def url = "/health"

        when:
        def result = mockMvc.perform(get(url)).andReturn()

        then:
        result.response.status == OK.value()
    }

    def "test check health"() {
        given:
        def url = "/readiness"

        when:
        def result = mockMvc.perform(get(url)).andReturn()

        then:
        result.response.status == OK.value()
    }
}

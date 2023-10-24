package com.ingress.msrating.controller

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class HealthControllerTest extends Specification {

    MockMvc mockMvc

    def setup() {
        def healthController = new HealthController()
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).build()
    }

    @Unroll
    def "test check health #endpoint"() {
        when:
        def result = mockMvc.perform(get(endpoint)).andReturn()

        then:
        result.response.status == OK.value()

        where:
        endpoint     | _
        '/health'    | _
        '/readiness' | _
    }
}

package com.ingress.msrating;

import com.ingress.msrating.model.constants.RabbitEnvironments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RabbitEnvironments.class)
public class MsRatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRatingApplication.class, args);
    }

}

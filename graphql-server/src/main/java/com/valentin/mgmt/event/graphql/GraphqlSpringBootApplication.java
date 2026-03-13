package com.valentin.mgmt.event.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.valentin.mgmt.event.graphql")
@EnableScheduling
public class GraphqlSpringBootApplication {
    static void main(String[] args) {
        SpringApplication.run(GraphqlSpringBootApplication.class, args);
    }
}

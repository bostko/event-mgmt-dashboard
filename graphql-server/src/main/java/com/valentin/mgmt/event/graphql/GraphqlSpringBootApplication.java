package com.valentin.mgmt.event.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.valentin.mgmt.event")
@EnableJpaRepositories("com.valentin.mgmt.event.domain.repository")
@EntityScan("com.valentin.mgmt.event.domain.entity")
public class GraphqlSpringBootApplication {
    static void main(String[] args) {
        SpringApplication.run(GraphqlSpringBootApplication.class, args);
    }
}

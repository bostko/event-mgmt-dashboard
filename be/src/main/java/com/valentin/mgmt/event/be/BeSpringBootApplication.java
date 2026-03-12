package com.valentin.mgmt.event.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.valentin.mgmt.event.domain.entity")
@EnableJpaRepositories("com.valentin.mgmt.event.domain.repository")
public class BeSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BeSpringBootApplication.class, args);
    }
}

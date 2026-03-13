package com.valentin.mgmt.event.generator;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories("com.valentin.mgmt.event.domain.repository")
@EntityScan("com.valentin.mgmt.event.domain.entity")
public class EventsGeneratorApplication {

    static void main(String[] args) {
        SpringApplication.run(EventsGeneratorApplication.class, args);
    }

    @Scheduled(fixedDelay = 3000)
    public void helloWorld() {
        new MgmtEventEntity();
    }
}

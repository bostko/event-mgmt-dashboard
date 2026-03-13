package com.valentin.mgmt.event.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class EventsGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsGeneratorApplication.class, args);
    }

    @Scheduled(fixedDelay = 3000)
    public void helloWorld() {
//        new MgmtEventEntity()
    }
}

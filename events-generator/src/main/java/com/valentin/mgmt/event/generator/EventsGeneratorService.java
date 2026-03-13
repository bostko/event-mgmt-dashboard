package com.valentin.mgmt.event.generator;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import com.valentin.mgmt.event.service.MgmtEnvironmentService;
import com.valentin.mgmt.event.service.MgmtEventService;
import com.valentin.mgmt.event.service.MgmtServiceService;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EventsGeneratorService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EventsGeneratorService.class);

    private final MgmtEventService mgmtEventService;
    private final MgmtEnvironmentService mgmtEnvironmentService;
    private final MgmtServiceService mgmtServiceService;

    public EventsGeneratorService(MgmtEventService mgmtEventService, MgmtServiceService mgmtServiceService, MgmtEnvironmentService mgmtEnvironmentService) {
        this.mgmtEventService = mgmtEventService;
        this.mgmtServiceService = mgmtServiceService;
        this.mgmtEnvironmentService = mgmtEnvironmentService;
    }

    @Scheduled(fixedDelay = 5000)
    public void helloWorld() {
        logger.info("======");
        logger.info("Generating a new batch of events");

        mgmtEnvironmentService.getAllEnvironments(0, 100).forEach((environment) -> {
            logger.info("Environment: {}", environment.getName());
            mgmtServiceService.getServicesByEnvironment(environment.getId()).forEach((service) -> {
                MgmtEventEntity event = mgmtEventService.createEvent("A test gen vent for " + service.getName(), Instant.now(), service);
                logger.info("[{}] Event: {} for service", service.getName(), event.getDescription());
            });
        });
    }
}

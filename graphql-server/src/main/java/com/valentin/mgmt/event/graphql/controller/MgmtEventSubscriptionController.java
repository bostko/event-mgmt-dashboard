package com.valentin.mgmt.event.graphql.controller;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import com.valentin.mgmt.event.service.MgmtEventService;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class MgmtEventSubscriptionController {

    private final MgmtEventService mgmtEventService;

    public MgmtEventSubscriptionController(MgmtEventService mgmtEventService) {
        this.mgmtEventService = mgmtEventService;
    }

    @SubscriptionMapping
    public Flux<MgmtEventEntity> mgmtEventCreated() {
        AtomicReference<Instant> lastSeen = new AtomicReference<>(Instant.now());
        return Flux.interval(Duration.ofSeconds(3))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tick -> {
                    Instant since = lastSeen.get();
                    List<MgmtEventEntity> newEvents = mgmtEventService.getEventsAfter(since);
                    if (!newEvents.isEmpty()) {
                        lastSeen.set(newEvents.getLast().getTimestamp());
                    }
                    return Flux.fromIterable(newEvents);
                });
    }
}
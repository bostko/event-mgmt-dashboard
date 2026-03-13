package com.valentin.mgmt.event.graphql.controller;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import com.valentin.mgmt.event.service.MgmtEventService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class MgmtEventController {

    private final MgmtEventService mgmtEventService;

    public MgmtEventController(MgmtEventService mgmtEventService) {
        this.mgmtEventService = mgmtEventService;
    }

    @QueryMapping
    public List<MgmtEventEntity> mgmtEvents() {
        return mgmtEventService.getAllEvents();
    }

    @QueryMapping
    public MgmtEventEntity mgmtEventById(@Argument String id) {
        return mgmtEventService.getEventById(UUID.fromString(id)).orElse(null);
    }

    @QueryMapping
    public List<MgmtEventEntity> mgmtEventsByServiceId(@Argument Long serviceId) {
        return mgmtEventService.getEventsByServiceId(serviceId);
    }
}
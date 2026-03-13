package com.valentin.mgmt.event.service;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class MgmtEventService {
    private final MgmtEventRepository repository;

    public MgmtEventService(MgmtEventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MgmtEventEntity createEvent(String description, Instant timestamp, MgmtServiceEntity service) {

        MgmtEventEntity newEvent = new MgmtEventEntity();
        newEvent.setDescription(description);
        newEvent.setTimestamp(timestamp);
        newEvent.setService(service);

        return repository.save(newEvent);
    }
}

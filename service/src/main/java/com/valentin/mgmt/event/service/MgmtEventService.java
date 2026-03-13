package com.valentin.mgmt.event.service;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<MgmtEventEntity> getEventsAfter(Instant since) {
        return repository.findByTimestampAfterOrderByTimestampAsc(since);
    }

    @Transactional(readOnly = true)
    public List<MgmtEventEntity> getAllEvents() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MgmtEventEntity> getEventById(UUID id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<MgmtEventEntity> getEventsByServiceId(Long serviceId) {
        return repository.findByServiceId(serviceId);
    }
}

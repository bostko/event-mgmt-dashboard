package com.valentin.mgmt.event.service;

import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MgmtEventGenerationService {
    private final MgmtEnvironmentRepository repository;

    public MgmtEventGenerationService(MgmtEnvironmentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MgmtEnvironmentEntity createEvent(String name) {

        MgmtEnvironmentEntity newEntity = new MgmtEnvironmentEntity();
        newEntity.setName(name);
        return repository.save(newEntity);
    }
}

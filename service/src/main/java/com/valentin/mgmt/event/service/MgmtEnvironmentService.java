package com.valentin.mgmt.event.service;

import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.exception.NotFoundException;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MgmtEnvironmentService {
    private final MgmtEnvironmentRepository repository;

    public MgmtEnvironmentService(MgmtEnvironmentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<MgmtEnvironmentEntity> getAllEnvironments(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public MgmtEnvironmentEntity createEnvironment(String name) {
        MgmtEnvironmentEntity newEntity = new MgmtEnvironmentEntity();
        newEntity.setName(name);
        return repository.save(newEntity);
    }

    public Optional<MgmtEnvironmentEntity> getEnvironment(Long id) {
        return repository.findById(id);
    }

    public MgmtEnvironmentEntity updateEnvironment(Long id, String name) {
        MgmtEnvironmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Environment not found: " + id));
        entity.setName(name);
        return repository.save(entity);
    }

    public void deleteEnvironment(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Environment not found: " + id);
        }
        repository.deleteById(id);
    }
}
package com.valentin.mgmt.event.be.rest.service;

import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MgmtEnvironmentService {
    private final MgmtEnvironmentRepository repository;

    public MgmtEnvironmentService(MgmtEnvironmentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<MgmtEnvironmentEntity> getAllEnvironments() {
        return repository.findAll(PageRequest.of(0,2)).stream().toList();
    }


    @Transactional()
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entity.setName(name);
        return repository.save(entity);
    }

    public void deleteEnvironment(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}

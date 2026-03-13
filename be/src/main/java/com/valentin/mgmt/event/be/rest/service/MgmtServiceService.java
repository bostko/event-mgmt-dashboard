package com.valentin.mgmt.event.be.rest.service;

import com.valentin.mgmt.event.be.rest.controller.MgmtServiceController;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtServiceRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MgmtServiceService {
    private final MgmtServiceRepository mgmtServiceRepository;
    private final MgmtEnvironmentService mgmtEnvironmentService;
    private final Environment environment;

    public MgmtServiceService(MgmtServiceRepository mgmtServiceRepository, MgmtEnvironmentService mgmtEnvironmentService, Environment environment) {
        this.mgmtServiceRepository = mgmtServiceRepository;
        this.mgmtEnvironmentService = mgmtEnvironmentService;
        this.environment = environment;
    }

    @Transactional
    public void deleteService(Long id) {
        if (!mgmtServiceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        mgmtServiceRepository.deleteById(id);
    }

    public List<MgmtServiceEntity> getServicesByEnvironment(Long id) {
        return mgmtServiceRepository.findByEnvironmentId(id);
    }

    @Transactional
    public MgmtServiceEntity createMgmtService(String name, String owner, Long environmentId) {
        var entity = new MgmtServiceEntity();
        entity.setName(name);
        entity.setOwner(owner);
        MgmtEnvironmentEntity environment = mgmtEnvironmentService.getEnvironment(environmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Environment not found: " + environmentId));
        entity.setEnvironment(environment);
        return mgmtServiceRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<MgmtServiceEntity> getAllServices() {
        return mgmtServiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MgmtServiceEntity> findByEnvironmentId(Long environmentId) {
        return mgmtServiceRepository.findByEnvironmentId(environmentId);
    }

    @Transactional(readOnly = true)
    public Optional<MgmtServiceEntity> findById(Long id) {
        return mgmtServiceRepository.findById(id);
    }

    @Transactional
    public MgmtServiceEntity updateService(Long id, Long environmentId, String name, String owner) {
        var entity = mgmtServiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entity.setName(name);
        entity.setOwner(owner);
        var env = mgmtEnvironmentService.getEnvironment(environmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Environment not found: " + environmentId));
        entity.setEnvironment(env);
        return mgmtServiceRepository.save(entity);
    }
}

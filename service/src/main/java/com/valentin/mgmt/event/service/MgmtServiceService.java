package com.valentin.mgmt.event.service;

import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.exception.NotFoundException;
import com.valentin.mgmt.event.domain.repository.MgmtServiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MgmtServiceService {
    private final MgmtServiceRepository mgmtServiceRepository;
    private final MgmtEnvironmentService mgmtEnvironmentService;

    public MgmtServiceService(MgmtServiceRepository mgmtServiceRepository, MgmtEnvironmentService mgmtEnvironmentService) {
        this.mgmtServiceRepository = mgmtServiceRepository;
        this.mgmtEnvironmentService = mgmtEnvironmentService;
    }

    @Transactional
    public void deleteService(Long id) {
        if (!mgmtServiceRepository.existsById(id)) {
            throw new NotFoundException("Service not found: " + id);
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
                .orElseThrow(() -> new NotFoundException("Environment not found: " + environmentId));
        entity.setEnvironment(environment);
        return mgmtServiceRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public Page<MgmtServiceEntity> getAllServices(int page, int size) {
        return mgmtServiceRepository.findAll(PageRequest.of(page, size));
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
                .orElseThrow(() -> new NotFoundException("Service not found: " + id));
        entity.setName(name);
        entity.setOwner(owner);
        var env = mgmtEnvironmentService.getEnvironment(environmentId)
                .orElseThrow(() -> new NotFoundException("Environment not found: " + environmentId));
        entity.setEnvironment(env);
        return mgmtServiceRepository.save(entity);
    }
}
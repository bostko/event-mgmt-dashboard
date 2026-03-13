package com.valentin.mgmt.event.be.rest.controller;

import com.valentin.mgmt.event.be.rest.dto.service.CreateMgmtServiceRequest;
import com.valentin.mgmt.event.be.rest.dto.service.MgmtServiceResponse;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import com.valentin.mgmt.event.domain.repository.MgmtServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MgmtServiceController {

    private final MgmtServiceRepository repository;
    private final MgmtEnvironmentRepository environmentRepository;

    public MgmtServiceController(MgmtServiceRepository repository, MgmtEnvironmentRepository environmentRepository) {
        this.repository = repository;
        this.environmentRepository = environmentRepository;
    }

    @PostMapping("/mgmt-service")
    public MgmtServiceResponse createMgmtService(@RequestBody CreateMgmtServiceRequest request) {
        var entity = new MgmtServiceEntity();
        entity.setName(request.name());
        entity.setOwner(request.owner());
        entity.setEnvironment(resolveEnvironment(request.environmentId()));
        var saved = repository.save(entity);
        return toResponse(saved);
    }

    @GetMapping("/mgmt-service/all")
    public Iterable<MgmtServiceResponse> getAllMgmtServices() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/mgmt-service/{id}")
    public MgmtServiceResponse getEnvironment(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/mgmt-service/{id}")
    public MgmtServiceResponse updateEnvironment(@PathVariable Long id, @RequestBody CreateMgmtServiceRequest request) {
        MgmtServiceEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entity.setName(request.name());
        entity.setOwner(request.owner());
        entity.setEnvironment(resolveEnvironment(request.environmentId()));
        var result = repository.save(entity);
        return toResponse(result);
    }

    @DeleteMapping("/mgmt-service/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnvironment(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    private MgmtEnvironmentEntity resolveEnvironment(Long environmentId) {
        if (environmentId == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "environmentId is required");
        return environmentRepository.findById(environmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Environment not found: " + environmentId));
    }

    private MgmtServiceResponse toResponse(MgmtServiceEntity entity) {
        Long environmentId = entity.getEnvironment() == null ? null : entity.getEnvironment().getId();
        return new MgmtServiceResponse(entity.getId(), entity.getName(), entity.getOwner(), environmentId);
    }
}
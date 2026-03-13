package com.valentin.mgmt.event.be.rest.controller;

import com.valentin.mgmt.event.be.rest.dto.environment.UpdateMgmtEnvironmentRequest;
import com.valentin.mgmt.event.be.rest.dto.environment.MgmtEnvironmentResponse;
import com.valentin.mgmt.event.be.rest.dto.service.MgmtServiceResponse;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MgmtEnvironmentController {
    private final MgmtEnvironmentRepository repository;

    public MgmtEnvironmentController(MgmtEnvironmentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/mgmt-environment")
    public MgmtEnvironmentResponse createEnvironment(@RequestBody UpdateMgmtEnvironmentRequest request) {
        MgmtEnvironmentEntity newEntity = new MgmtEnvironmentEntity();
        newEntity.setName(request.name());
        var result = repository.save(newEntity);
        return toResponse(result);
    }

    @GetMapping("/mgmt-environment/all")
    public Iterable<MgmtEnvironmentResponse> getAllEnvironments() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/mgmt-environment/{id}")
    public MgmtEnvironmentResponse getEnvironment(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/mgmt-environment/{id}")
    public MgmtEnvironmentResponse updateEnvironment(@PathVariable Long id, @RequestBody UpdateMgmtEnvironmentRequest request) {
        MgmtEnvironmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        entity.setName(request.name());
        var result = repository.save(entity);
        return toResponse(result);
    }

    @DeleteMapping("/mgmt-environment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnvironment(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    private MgmtEnvironmentResponse toResponse(MgmtEnvironmentEntity entity) {
        List<MgmtServiceResponse> services = entity.getServices() == null ? List.of() :
                entity.getServices().stream().map(this::toServiceResponse).toList();
        return new MgmtEnvironmentResponse(entity.getId(), entity.getName(), services);
    }

    private MgmtServiceResponse toServiceResponse(MgmtServiceEntity entity) {
        Long environmentId = entity.getEnvironment() == null ? null : entity.getEnvironment().getId();
        return new MgmtServiceResponse(entity.getId(), entity.getName(), entity.getOwner(), environmentId);
    }
}
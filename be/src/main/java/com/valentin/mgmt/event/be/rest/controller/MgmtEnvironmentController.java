package com.valentin.mgmt.event.be.rest.controller;

import com.valentin.mgmt.event.be.rest.dto.environment.MgmtEnvironmentResponse;
import com.valentin.mgmt.event.be.rest.dto.environment.UpdateMgmtEnvironmentRequest;
import com.valentin.mgmt.event.be.rest.dto.service.MgmtServiceResponse;
import com.valentin.mgmt.event.be.rest.service.MgmtEnvironmentService;
import com.valentin.mgmt.event.be.rest.service.MgmtServiceService;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;

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

import java.util.List;

@RestController
public class MgmtEnvironmentController {
    private final MgmtEnvironmentService mgmtEnvironmentService;
    private final MgmtServiceService mgmtServiceService;

    public MgmtEnvironmentController(MgmtEnvironmentService mgmtEnvironmentService, MgmtServiceService mgmtServiceService) {
        this.mgmtEnvironmentService = mgmtEnvironmentService;
        this.mgmtServiceService = mgmtServiceService;
    }

    @PostMapping("/mgmt-environment")
    public MgmtEnvironmentResponse createEnvironment(@RequestBody UpdateMgmtEnvironmentRequest request) {
        var result = mgmtEnvironmentService.createEnvironment(request.name());
        return toResponse(result);
    }

    @GetMapping("/mgmt-environment/all")
    public Iterable<MgmtEnvironmentResponse> getAllEnvironments() {
        return mgmtEnvironmentService.getAllEnvironments().stream().map(this::toResponse).toList();
    }

    @GetMapping("/mgmt-environment/{id}")
    public MgmtEnvironmentResponse getEnvironment(@PathVariable Long id) {
        return mgmtEnvironmentService.getEnvironment(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/mgmt-environment/{id}/services")
    public Iterable<MgmtServiceResponse> getServicesByEnvironment(@PathVariable Long id) {
        return mgmtServiceService.getServicesByEnvironment(id).stream().map(this::toServiceResponse).toList();
    }

    @PutMapping("/mgmt-environment/{id}")
    public MgmtEnvironmentResponse updateEnvironment(@PathVariable Long id, @RequestBody UpdateMgmtEnvironmentRequest request) {
        var result = mgmtEnvironmentService.updateEnvironment(id, request.name());
        return toResponse(result);
    }

    @DeleteMapping("/mgmt-environment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnvironment(@PathVariable Long id) {
        mgmtEnvironmentService.deleteEnvironment(id);
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
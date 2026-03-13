package com.valentin.mgmt.event.be.rest.controller;

import com.valentin.mgmt.event.be.rest.dto.service.CreateMgmtServiceRequest;
import com.valentin.mgmt.event.be.rest.dto.service.MgmtServiceResponse;
import com.valentin.mgmt.event.be.rest.service.MgmtServiceService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MgmtServiceController {
    private final MgmtServiceService mgmtServiceService;

    public MgmtServiceController(MgmtServiceService mgmtServiceService) {
        this.mgmtServiceService = mgmtServiceService;
    }

    @PostMapping("/mgmt-service")
    public MgmtServiceResponse createMgmtService(@RequestBody CreateMgmtServiceRequest request) {
        if (request.environmentId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        var saved = mgmtServiceService.createMgmtService(request.name(), request.owner(), request.environmentId());
        return toResponse(saved);
    }

    @GetMapping("/mgmt-service/all")
    public Iterable<MgmtServiceResponse> getAllMgmtServices() {
        return mgmtServiceService.getAllServices().stream().map(MgmtServiceController::toResponse).toList();
    }

    @GetMapping("/mgmt-service/byEnvironmentId/{environmentId}")
    public Iterable<MgmtServiceResponse> getAllMgmtServicesByEnvironmentId(@PathVariable Long environmentId) {
        return mgmtServiceService.findByEnvironmentId(environmentId).stream().map(MgmtServiceController::toResponse).toList();
    }

    @GetMapping("/mgmt-service/{id}")
    public MgmtServiceResponse getEnvironment(@PathVariable Long id) {
        return mgmtServiceService.findById(id)
                .map(MgmtServiceController::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/mgmt-service/{id}")
    public MgmtServiceResponse updateEnvironment(@PathVariable Long id, @RequestParam Long environmentId, @RequestBody CreateMgmtServiceRequest request) {
        MgmtServiceEntity result = mgmtServiceService.updateService(id, environmentId, request.name(), request.owner());
        return toResponse(result);
    }

    @DeleteMapping("/mgmt-service/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnvironment(@PathVariable Long id) {
        mgmtServiceService.deleteService(id);
    }

    static MgmtServiceResponse toResponse(MgmtServiceEntity entity) {
        Long environmentId = entity.getEnvironment() == null ? null : entity.getEnvironment().getId();
        return new MgmtServiceResponse(entity.getId(), entity.getName(), entity.getOwner(), environmentId);
    }
}
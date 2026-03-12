package com.valentin.mgmt.event.be.rest.controller;

import com.valentin.mgmt.event.be.rest.dto.environment.CreateMgmtEnvironmentRequest;
import com.valentin.mgmt.event.be.rest.dto.environment.MgmtEnvironmentResponse;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MgmtEnvironmentController {
    private final MgmtEnvironmentRepository repository;

    public MgmtEnvironmentController(MgmtEnvironmentRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/mgmt-environment")
    public MgmtEnvironmentResponse createEnvironment(@RequestBody CreateMgmtEnvironmentRequest request) {
        MgmtEnvironmentEntity newEntity = new MgmtEnvironmentEntity();
        newEntity.setName(request.name());
        var result = repository.save(newEntity);
        return new MgmtEnvironmentResponse(result.getId(), result.getName());
    }
}

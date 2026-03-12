package com.valentin.mgmt.event.be.rest.controller;

import com.valentin.mgmt.event.be.rest.dto.CreateMgmtServiceRequest;
import com.valentin.mgmt.event.be.rest.dto.MgmtServiceResponse;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtServiceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MgmtServiceController {

    private final MgmtServiceRepository repository;

    public MgmtServiceController(MgmtServiceRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/mgmt-service")
    public MgmtServiceResponse createMgmtService(@RequestBody CreateMgmtServiceRequest request) {
        var entity = new MgmtServiceEntity();
        entity.setName(request.name());
        entity.setOwner(request.owner());
        var saved = repository.save(entity);
        return new MgmtServiceResponse(saved.getId(), saved.getName(), saved.getOwner());
    }

    @GetMapping("/mgmt-service/all")
    public Iterable<MgmtServiceResponse> getAllMgmtServices() {
        return repository.findAll().stream().map(entity -> new MgmtServiceResponse(entity.getId(), entity.getName(), entity.getOwner())).toList();
    }
}

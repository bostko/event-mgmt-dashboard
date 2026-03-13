package com.valentin.mgmt.event.rest.dto.environment;

import com.valentin.mgmt.event.rest.dto.service.MgmtServiceResponse;

import java.util.List;

public record MgmtEnvironmentResponse(Long id, String name, List<MgmtServiceResponse> services) {
}
package com.valentin.mgmt.event.rest.dto.service;

public record CreateMgmtServiceRequest(String name, String owner, Long environmentId) {
}
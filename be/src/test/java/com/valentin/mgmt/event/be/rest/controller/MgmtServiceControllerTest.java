package com.valentin.mgmt.event.be.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
import com.valentin.mgmt.event.domain.repository.MgmtServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MgmtServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MgmtServiceRepository repository;

    @Autowired
    private MgmtEnvironmentRepository environmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MgmtEnvironmentEntity defaultEnv;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        environmentRepository.deleteAll();
        defaultEnv = savedEnvironment("production");
    }

    @Test
    void createService_returnsCreatedService() throws Exception {
        mockMvc.perform(post("/mgmt-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "auth-service", "owner", "team-a", "environmentId", defaultEnv.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("auth-service"))
                .andExpect(jsonPath("$.owner").value("team-a"))
                .andExpect(jsonPath("$.environmentId").value(defaultEnv.getId()));
    }

    @Test
    void createService_withoutEnvironmentId_returns400() throws Exception {
        mockMvc.perform(post("/mgmt-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "auth-service", "owner", "team-a"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createService_withInvalidEnvironmentId_returns400() throws Exception {
        mockMvc.perform(post("/mgmt-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "auth-service", "owner", "team-a", "environmentId", 999L))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllServices_returnsAllServices() throws Exception {
        savedEntity("auth-service", "team-a");
        savedEntity("payment-service", "team-b");

        mockMvc.perform(get("/mgmt-service/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAllServices_whenEmpty_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/mgmt-service/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getService_whenExists_returnsService() throws Exception {
        MgmtServiceEntity entity = savedEntity("auth-service", "team-a");

        mockMvc.perform(get("/mgmt-service/{id}", entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value("auth-service"))
                .andExpect(jsonPath("$.owner").value("team-a"))
                .andExpect(jsonPath("$.environmentId").value(defaultEnv.getId()));
    }

    @Test
    void getService_whenNotExists_returns404() throws Exception {
        mockMvc.perform(get("/mgmt-service/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateService_whenExists_returnsUpdatedService() throws Exception {
        MgmtServiceEntity entity = savedEntity("auth-service", "team-a");
        MgmtEnvironmentEntity otherEnv = savedEnvironment("staging");

        mockMvc.perform(put("/mgmt-service/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "auth-service-v2", "owner", "team-b", "environmentId", otherEnv.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value("auth-service-v2"))
                .andExpect(jsonPath("$.owner").value("team-b"))
                .andExpect(jsonPath("$.environmentId").value(otherEnv.getId()));
    }

    @Test
    void updateService_withoutEnvironmentId_returns400() throws Exception {
        MgmtServiceEntity entity = savedEntity("auth-service", "team-a");

        mockMvc.perform(put("/mgmt-service/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "auth-service", "owner", "team-a"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateService_whenNotExists_returns404() throws Exception {
        mockMvc.perform(put("/mgmt-service/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "auth-service", "owner", "team-a", "environmentId", defaultEnv.getId()))))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteService_whenExists_returns204() throws Exception {
        MgmtServiceEntity entity = savedEntity("auth-service", "team-a");

        mockMvc.perform(delete("/mgmt-service/{id}", entity.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteService_whenNotExists_returns404() throws Exception {
        mockMvc.perform(delete("/mgmt-service/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    private MgmtServiceEntity savedEntity(String name, String owner) {
        MgmtServiceEntity entity = new MgmtServiceEntity();
        entity.setName(name);
        entity.setOwner(owner);
        entity.setEnvironment(defaultEnv);
        return repository.save(entity);
    }

    private MgmtEnvironmentEntity savedEnvironment(String name) {
        MgmtEnvironmentEntity entity = new MgmtEnvironmentEntity();
        entity.setName(name);
        return environmentRepository.save(entity);
    }
}
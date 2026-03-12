package com.valentin.mgmt.event.be.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import com.valentin.mgmt.event.domain.repository.MgmtEnvironmentRepository;
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
class MgmtEnvironmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MgmtEnvironmentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void createEnvironment_returnsCreatedEnvironment() throws Exception {
        mockMvc.perform(post("/mgmt-environment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "production"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("production"));
    }

    @Test
    void getAllEnvironments_returnsAllEnvironments() throws Exception {
        savedEntity("staging");
        savedEntity("production");

        mockMvc.perform(get("/mgmt-environment/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAllEnvironments_whenEmpty_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/mgmt-environment/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getEnvironment_whenExists_returnsEnvironment() throws Exception {
        MgmtEnvironmentEntity entity = savedEntity("staging");

        mockMvc.perform(get("/mgmt-environment/{id}", entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value("staging"));
    }

    @Test
    void getEnvironment_whenNotExists_returns404() throws Exception {
        mockMvc.perform(get("/mgmt-environment/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEnvironment_whenExists_returnsUpdatedEnvironment() throws Exception {
        MgmtEnvironmentEntity entity = savedEntity("staging");

        mockMvc.perform(put("/mgmt-environment/{id}", entity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "production"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value("production"));
    }

    @Test
    void updateEnvironment_whenNotExists_returns404() throws Exception {
        mockMvc.perform(put("/mgmt-environment/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("name", "production"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEnvironment_whenExists_returns204() throws Exception {
        MgmtEnvironmentEntity entity = savedEntity("staging");

        mockMvc.perform(delete("/mgmt-environment/{id}", entity.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEnvironment_whenNotExists_returns404() throws Exception {
        mockMvc.perform(delete("/mgmt-environment/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    private MgmtEnvironmentEntity savedEntity(String name) {
        MgmtEnvironmentEntity entity = new MgmtEnvironmentEntity();
        entity.setName(name);
        return repository.save(entity);
    }
}
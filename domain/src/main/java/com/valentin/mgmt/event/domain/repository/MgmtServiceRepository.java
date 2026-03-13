package com.valentin.mgmt.event.domain.repository;

import com.valentin.mgmt.event.domain.entity.MgmtServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MgmtServiceRepository extends JpaRepository<MgmtServiceEntity, Long> {
    List<MgmtServiceEntity> findByEnvironmentId(Long environmentId);
}
package com.valentin.mgmt.event.domain.repository;

import com.valentin.mgmt.event.domain.entity.MgmtEnvironmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MgmtEnvironmentRepository extends JpaRepository<MgmtEnvironmentEntity, Long> {
}

package com.valentin.mgmt.event.domain.repository;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MgmtEventRepository extends JpaRepository<MgmtEventEntity, UUID> {
    List<MgmtEventEntity> findByServiceId(Long serviceId);
}

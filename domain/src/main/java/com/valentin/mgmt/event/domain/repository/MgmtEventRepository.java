package com.valentin.mgmt.event.domain.repository;

import com.valentin.mgmt.event.domain.entity.MgmtEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MgmtEventRepository extends JpaRepository<MgmtEventEntity, Long> {
}

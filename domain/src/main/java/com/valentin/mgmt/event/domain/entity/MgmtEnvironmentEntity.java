package com.valentin.mgmt.event.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MgmtEnvironmentEntity {
    @Id
    @Getter
    private Long id;

    @Getter @Setter
    private String name;
}

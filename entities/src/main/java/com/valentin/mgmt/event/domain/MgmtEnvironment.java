package com.valentin.mgmt.event.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MgmtEnvironment {
    @Id
    private String id;

    @Getter
    private String name;
}

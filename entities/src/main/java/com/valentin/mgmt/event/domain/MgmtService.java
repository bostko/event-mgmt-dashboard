package com.valentin.mgmt.event.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MgmtService {
    @Id
    private String id;

    @Getter
    private String name;

    @Getter
    private String owner;
}

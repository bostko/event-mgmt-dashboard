package com.valentin.mgmt.event.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class MgmtServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String owner;

    // fetch = jakarta.persistence.FetchType.EAGER (do I need that one)
    @Getter @Setter
    @ManyToOne(optional = false)
    private MgmtEnvironmentEntity environment;
}

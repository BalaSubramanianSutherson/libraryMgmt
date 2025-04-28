package com.example.libraryMgmt.model;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, length = 20)
    private String createdBy;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false, length = 20)
    private String updatedBy;

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    public Role(String roleId) {
        this.id = UUID.fromString(roleId);
    }

    public Role() {}
}

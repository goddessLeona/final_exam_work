package com.petra.final_exam_work.Entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_uuid", updatable = false, unique = true, nullable = false)
    private UUID publicUuid;

    @Column (name = "role", nullable = false, unique = true)
    private String role;

    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicUuid() {
        return publicUuid;
    }

    public void setPublicUuid(UUID publicUuid) {
        this.publicUuid = publicUuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

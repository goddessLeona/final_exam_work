package com.petra.final_exam_work.Entity.user;

import com.petra.final_exam_work.Entity.junktionTables.userConcentform.UserConsentForm;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "public_uuid",
            updatable = false,
            unique = true,
            nullable = false
    )
    private UUID publicUuid;

    @Column(name = "user_name" , nullable = false, unique = true)
    private String userName;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "citext"
    )
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "is_contributor", nullable = false)
    private Boolean isContributor = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Instant createdAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserConsentForm> consentForms = new HashSet<>();

    @PrePersist
    private void prePersist() {
        if (publicUuid == null) {
            publicUuid = UUID.randomUUID();
        }
    }

    public User() {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getContributor() {
        return isContributor;
    }

    public void setContributor(Boolean contributor) {
        isContributor = contributor;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<UserConsentForm> getConsentForms() {
        return consentForms;
    }

    public void setConsentForms(Set<UserConsentForm> consentForms) {
        this.consentForms = consentForms;
    }
}

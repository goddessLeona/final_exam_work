package com.petra.final_exam_work.entity.user;

import com.petra.final_exam_work.entity.enums.ContributorStatus;
import com.petra.final_exam_work.entity.junktionTables.userConcentform.UserConsentForm;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private String username;

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
    private boolean contributor;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name="contributor_status", nullable = false, columnDefinition = "contributor_status")
    private ContributorStatus contributorStatus = ContributorStatus.NOT_APPLIED;

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

    public User(Long id, UUID publicUuid, String username, String password, String email, String firstName,
                String lastName, boolean contributor, ContributorStatus contributorStatus, Instant createdAt,
                Set<Role> roles, Set<UserConsentForm> consentForms) {
        this.id = id;
        this.publicUuid = publicUuid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contributor = contributor;
        this.contributorStatus = contributorStatus;
        this.createdAt = createdAt;
        this.roles = roles;
        this.consentForms = consentForms;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isContributor() {
        return contributor;
    }

    public void setContributor(boolean contributor) {
        this.contributor = contributor;
    }

    public ContributorStatus getContributorStatus() {
        return contributorStatus;
    }

    public void setContributorStatus(ContributorStatus contributorStatus) {
        this.contributorStatus = contributorStatus;
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

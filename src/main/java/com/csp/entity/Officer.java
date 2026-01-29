package com.csp.entity;

import jakarta.persistence.*;

/**
 * Officer Entity
 * ----------------
 * Represents OFFICER users in the system.
 * Officers perform final approval or rejection of applications.
 *
 * This entity supports Spring Security authentication.
 */
@Entity
@Table(name = "officer")
public class Officer {

    /**
     * Primary key of the officer table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "officer_id")
    private int officerId;

    /**
     * Officer full name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Officer email (USED AS USERNAME FOR LOGIN).
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Encrypted password (BCrypt).
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Officer designation.
     * Example: Tehsildar, Revenue Officer.
     */
    @Column(name = "designation")
    private String designation;

    /**
     * Role assigned to the officer.
     * Example: ROLE_OFFICER
     */
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * Account enabled flag (required by Spring Security).
     */
    @Column(name = "enabled")
    private boolean enabled = true;

    /**
     * Status of the officer account (ACTIVE / INACTIVE).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    /* ---------------- Constructors ---------------- */

    public Officer() {}

    public Officer(int officerId, String name, String email,
                   String password, String designation,
                   String role, boolean enabled, Status status) {

        this.officerId = officerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.designation = designation;
        this.role = role;
        this.enabled = enabled;
        this.status = status;
    }

    /* ---------------- Getters & Setters ---------------- */

    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** Used by Spring Security as username */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** Password must always be encoded before saving */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /** Must be ROLE_OFFICER */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

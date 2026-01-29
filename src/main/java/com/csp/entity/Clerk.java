package com.csp.entity;

import jakarta.persistence.*;

/**
 * Clerk Entity
 * -------------
 * Represents CLERK users in the system.
 * Clerks verify documents submitted by citizens.
 *
 * This entity is SECURITY-ENABLED and supports login.
 */
@Entity
@Table(name = "clerk")
public class Clerk {

    /**
     * Primary key of the clerk table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clerk_id")
    private int clerkId;

    /**
     * Clerk full name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Clerk email (USED AS USERNAME FOR LOGIN).
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Encrypted password (BCrypt).
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Role assigned to the clerk.
     * Example: ROLE_CLERK
     */
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * Account enabled status.
     * Required by Spring Security.
     */
    @Column(name = "enabled")
    private boolean enabled = true;

    /**
     * Status of the clerk account (ACTIVE / INACTIVE).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    /* ---------------- Constructors ---------------- */

    public Clerk() {}

    public Clerk(int clerkId, String name, String email,
                 String password, String role,
                 boolean enabled, Status status) {
        this.clerkId = clerkId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.status = status;
    }

    /* ---------------- Getters & Setters ---------------- */

    public int getClerkId() {
        return clerkId;
    }

    public void setClerkId(int clerkId) {
        this.clerkId = clerkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    /** Password must always be encoded before saving */
    public void setPassword(String password) {
        this.password = password;
    }

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

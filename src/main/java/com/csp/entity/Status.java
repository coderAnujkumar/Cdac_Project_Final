package com.csp.entity;

import jakarta.persistence.*;

/**
 * Status Entity
 * ----------------
 * Represents the STATUS master table in the database.
 * Stores system-wide statuses such as ACTIVE, PENDING,
 * APPROVED, and REJECTED.
 *
 * This entity is referenced by Citizen, Clerk, and Officer
 * entities to maintain consistency and normalization.
 */
@Entity
@Table(name = "status")
public class Status {

    /**
     * Primary key of the status table.
     * Auto-generated using IDENTITY strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int statusId;

    /**
     * Name of the status.
     * Must be unique and non-null.
     */
    @Column(name = "status_name", nullable = false, unique = true, length = 50)
    private String statusName;

    /**
     * Description of the status.
     * Provides additional details.
     */
    @Column(name = "description", length = 255)
    private String description;

    // Default constructor required by JPA
    public Status() {
    }

    // Parameterized constructor
    public Status(int statusId, String statusName, String description) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.description = description;
    }

    // Getters and Setters
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

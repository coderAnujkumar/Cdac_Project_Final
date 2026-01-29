package com.csp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * AuditReport Entity
 * -------------------
 * This class represents the AUDIT_REPORT table in the database.
 * It is used to store audit logs related to system activities
 * such as approvals, rejections, and administrative actions.
 *
 * This entity helps maintain transparency and accountability
 * within the Citizen Service Management System.
 */
@Entity                          // Marks this class as a JPA entity
@Table(name = "audit_report")    // Maps this entity to the "audit_report" table
public class AuditReport {

    /**
     * Primary key of the audit_report table.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    /**
     * Date on which the audit record was created.
     */
    @Column(name = "created_date")
    private LocalDate createdDate;

    /**
     * Description of the action performed.
     * Example: "Application Approved by Officer",
     *          "Document Verified by Clerk"
     */
    @Column(name = "action", nullable = false, length = 255)
    private String action;

    /**
     * Default constructor required by JPA.
     */
    public AuditReport() {
    }

    /**
     * Parameterized constructor used to create
     * AuditReport objects with predefined values.
     *
     * @param reportId unique identifier of the audit record
     * @param createdDate date of audit entry creation
     * @param action description of the performed action
     */
    public AuditReport(int reportId, LocalDate createdDate, String action) {
        this.reportId = reportId;
        this.createdDate = createdDate;
        this.action = action;
    }

    /**
     * Getter for reportId.
     * @return unique identifier of the audit record
     */
    public int getReportId() {
        return reportId;
    }

    /**
     * Setter for reportId.
     * @param reportId unique identifier to set
     */
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    /**
     * Getter for createdDate.
     * @return date when the audit record was created
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Setter for createdDate.
     * @param createdDate date to set
     */
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Getter for action description.
     * @return description of the action performed
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for action description.
     * @param action description of the performed action
     */
    public void setAction(String action) {
        this.action = action;
    }
}

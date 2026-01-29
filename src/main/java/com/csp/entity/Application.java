package com.csp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Application Entity
 * -------------------
 * This class represents the APPLICATION table in the database.
 *
 * It stores details of service applications submitted by citizens
 * such as service type, purpose, address, status, and submission date.
 *
 * The Application entity is the core entity of the system and
 * acts as a link between:
 * - Citizen
 * - Documents
 * - Verification workflow
 * - Approval process
 */
@Entity                          // Marks this class as a JPA entity
@Table(name = "application")     // Maps this entity to the "application" table
public class Application {

    /**
     * Primary key of the application table.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private int applicationId;

    /**
     * Date on which the application was submitted by the citizen.
     * Automatically set during application creation.
     */
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    /**
     * Type of service requested by the citizen.
     * Examples:
     * - Income Certificate
     * - Caste Certificate
     * - Domicile Certificate
     */
    @Column(name = "service_type", nullable = false)
    private String serviceType;

    /**
     * Purpose for which the service is requested.
     * Examples:
     * - Education
     * - Job Application
     * - Scholarship
     */
    @Column(name = "purpose", nullable = false, length = 255)
    private String purpose;

    /**
     * Address provided by the citizen during application submission.
     * Used for verification and record purposes.
     */
    @Column(name = "address", nullable = false, length = 500)
    private String address;

    /**
     * Current status of the application.
     * Typical values:
     * - PENDING
     * - VERIFIED
     * - APPROVED
     * - REJECTED
     *
     * Status changes during the application workflow.
     */
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "certificate_path")
    private String certificatePath;
    
    @Column(length = 500)
    private String rejectionReason;

    private String rejectedBy;   // Clerk name or Officer name



    /**
     * Citizen who submitted this application.
     * Relationship:
     * - One Citizen can submit many Applications
     *
     * Lazy fetching is used to improve performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    /**
     * Default constructor required by JPA.
     */
    public Application() {
    }

    /**
     * Parameterized constructor used to create
     * Application objects with predefined values.
     *
     * @param applicationId unique identifier of the application
     * @param applicationDate date of submission
     * @param serviceType requested service type
     * @param purpose purpose of the application
     * @param address address provided by the citizen
     * @param status current status of the application
     * @param citizen citizen who submitted the application
     */
    public Application(int applicationId,
                       LocalDate applicationDate,
                       String serviceType,
                       String purpose,
                       String address,
                       String status,
                       String certificatePath,
                       String rejectionReason,
                       String rejectedBy,
                       Citizen citizen) {

        this.applicationId = applicationId;
        this.applicationDate = applicationDate;
        this.serviceType = serviceType;
        this.purpose = purpose;
        this.address = address;
        this.status = status;
        this.certificatePath=certificatePath;
        this.rejectionReason=rejectionReason;
        this.rejectedBy=rejectedBy;
        this.citizen = citizen;
    }

    // -------------------- Getters and Setters --------------------

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    public String getCertificatePath() {
		return certificatePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
    
    
}

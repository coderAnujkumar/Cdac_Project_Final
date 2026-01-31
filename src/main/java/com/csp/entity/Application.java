package com.csp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private int applicationId;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "purpose", nullable = false, length = 255)
    private String purpose;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "certificate_path")
    private String certificatePath;

    @Column(length = 500)
    private String rejectionReason;

    private String rejectedBy;

    @Column(name = "clerk_verified")
    private Boolean clerkVerified = false;

    @Column(name = "verified_by")
    private String verifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    public Application() {
    }

    public Application(int applicationId, LocalDate applicationDate, String serviceType, String purpose,
                       String address, String status, String certificatePath, String rejectionReason,
                       String rejectedBy, Boolean clerkVerified, String verifiedBy, Citizen citizen) {

        this.applicationId = applicationId;
        this.applicationDate = applicationDate;
        this.serviceType = serviceType;
        this.purpose = purpose;
        this.address = address;
        this.status = status;
        this.certificatePath = certificatePath;
        this.rejectionReason = rejectionReason;
        this.rejectedBy = rejectedBy;
        this.clerkVerified = clerkVerified;
        this.verifiedBy = verifiedBy;
        this.citizen = citizen;
    }

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCertificatePath() { return certificatePath; }
    public void setCertificatePath(String certificatePath) { this.certificatePath = certificatePath; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getRejectedBy() { return rejectedBy; }
    public void setRejectedBy(String rejectedBy) { this.rejectedBy = rejectedBy; }

    public Boolean getClerkVerified() { return clerkVerified; }
    public void setClerkVerified(Boolean clerkVerified) { this.clerkVerified = clerkVerified; }

    public String getVerifiedBy() { return verifiedBy; }
    public void setVerifiedBy(String verifiedBy) { this.verifiedBy = verifiedBy; }

    public Citizen getCitizen() { return citizen; }
    public void setCitizen(Citizen citizen) { this.citizen = citizen; }
}
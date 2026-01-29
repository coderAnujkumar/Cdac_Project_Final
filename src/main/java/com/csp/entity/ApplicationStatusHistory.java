package com.csp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private String oldStatus;
    private String newStatus;

    // Who changed status (CLERK / OFFICER / ADMIN)
    private String changedByRole;

    // Optional: username/email
    private String changedBy;

    private LocalDateTime changedAt;

    // ===== Constructors =====
    public ApplicationStatusHistory() {}

    public ApplicationStatusHistory(Application application, String oldStatus,
                                    String newStatus, String changedByRole,
                                    String changedBy) {
        this.application = application;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedByRole = changedByRole;
        this.changedBy = changedBy;
        this.changedAt = LocalDateTime.now();
    }
    
 // ===== Getters & Setters =====

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public String getChangedByRole() {
		return changedByRole;
	}

	public void setChangedByRole(String changedByRole) {
		this.changedByRole = changedByRole;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public LocalDateTime getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		this.changedAt = changedAt;
	}

    
    
}

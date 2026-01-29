package com.csp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.AuditReport;

/**
 * AuditReportRepository
 * ---------------------
 * Repository interface for AuditReport entity.
 * Used to store and retrieve audit logs for system monitoring.
 */
public interface AuditReportRepository extends JpaRepository<AuditReport, Integer> {
}

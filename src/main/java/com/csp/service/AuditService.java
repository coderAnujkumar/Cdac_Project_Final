package com.csp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csp.entity.AuditReport;
import com.csp.repository.AuditReportRepository;

/**
 * AuditService
 * -------------
 * Handles logging of important system activities such as
 * approvals, rejections, and administrative actions.
 */
@Service
public class AuditService {

    @Autowired
    private AuditReportRepository auditReportRepository;

    /**
     * Log an audit action in the system.
     *
     * @param action description of the performed action
     */
    public void logAction(String action) {

        AuditReport report = new AuditReport();
        report.setAction(action);
        report.setCreatedDate(LocalDate.now());

        auditReportRepository.save(report);
    }
}

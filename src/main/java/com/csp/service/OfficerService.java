package com.csp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Officer;
import com.csp.repository.ApplicationRepository;
import com.csp.repository.OfficerRepository;

@Service
public class OfficerService {

    private final ApplicationRepository applicationRepository;
    private final CertificateService certificateService;
    private final StatusHistoryService historyService;
    private final OfficerRepository officerRepository;
    

    public OfficerService(ApplicationRepository applicationRepository,
                          CertificateService certificateService,
                          StatusHistoryService historyService,
                          OfficerRepository officerRepository ) {
        this.applicationRepository = applicationRepository;
        this.certificateService = certificateService;
        this.historyService = historyService;
        this.officerRepository=officerRepository;
    }

    // ================= FETCH VERIFIED =================
    public List<Application> getVerifiedApplications() {
        return applicationRepository.findByStatus("VERIFIED");
    }

    public Application getApplicationById(int applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found with ID: " + applicationId));
    }

    // ================= APPROVE =================
    public void approveApplication(int applicationId) {
        Application application = getApplicationById(applicationId);

        String oldStatus = application.getStatus();
        application.setStatus("APPROVED");

        try {
            // 🔥 Generate certificate
            String certPath = certificateService.generateCertificate(applicationId);
            application.setCertificatePath(certPath);
        } catch (IOException e) {
            throw new RuntimeException("Certificate generation failed", e);
        }

        applicationRepository.save(application);

        // 🧾 Log history
        historyService.logStatusChange(
                application,
                oldStatus,
                "APPROVED",
                "OFFICER",
                "OfficerUser" // replace with logged-in username later
        );
    }

    // ================= REJECT =================
    public void rejectApplication(int applicationId, String reason, String officerName) {
        Application application = getApplicationById(applicationId);

        String oldStatus = application.getStatus();
        application.setStatus("REJECTED_BY_OFFICER");
        application.setRejectionReason(reason);
        application.setRejectedBy("Officer: " + officerName);
        applicationRepository.save(application);

        // 🧾 Log history
        historyService.logStatusChange(
                application,
                oldStatus,
                "REJECTED_BY_OFFICER",
                "OFFICER",
                "OfficerUser"
        );
    }
    
    public Officer getOfficerByEmail(String email) {
        return officerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Officer not found"));
    }
    
    public List<Application> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(status);
    }

}

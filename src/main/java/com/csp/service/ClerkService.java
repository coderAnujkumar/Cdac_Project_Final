package com.csp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Clerk;
import com.csp.repository.ApplicationRepository;
import com.csp.repository.ClerkRepository;

@Service
public class ClerkService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StatusHistoryService historyService;

    @Autowired
    private ClerkRepository clerkRepository;

    // Fetch all submitted applications
    public List<Application> getSubmittedApplications() {
        return applicationRepository.findByStatus("SUBMITTED");
    }

    // Get application by ID
    public Application getApplicationById(int applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    // Verify application and mark clerk verification
    public void verifyApplication(int applicationId, String clerkName) {

        Application app = getApplicationById(applicationId);
        String oldStatus = app.getStatus();

        app.setClerkVerified(true);       // Mark clerk verification done
        app.setVerifiedBy(clerkName);     // Store clerk name
        app.setStatus("VERIFIED");        // Update workflow status

        applicationRepository.save(app);

        historyService.logStatusChange(
                app,
                oldStatus,
                "VERIFIED",
                "CLERK",
                clerkName
        );
    }

    // Reject application by clerk
    public void rejectApplication(int applicationId, String reason, String clerkName) {

        Application app = getApplicationById(applicationId);
        String oldStatus = app.getStatus();

        app.setStatus("REJECTED_BY_CLERK");   // Mark rejection stage
        app.setRejectionReason(reason);       // Save reason
        app.setRejectedBy("Clerk: " + clerkName); // Store who rejected

        applicationRepository.save(app);

        historyService.logStatusChange(
                app,
                oldStatus,
                "REJECTED_BY_CLERK",
                "CLERK",
                clerkName
        );
    }

    // Get clerk by email
    public Clerk getClerkByEmail(String email) {
        return clerkRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Clerk not found"));
    }
}
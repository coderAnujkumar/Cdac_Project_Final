package com.csp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Clerk;
import com.csp.repository.ApplicationRepository;
import com.csp.repository.ClerkRepository;

/**
 * ClerkService
 * -------------
 * This service handles all business logic related to
 * CLERK operations in the system.
 *
 * Clerk Responsibilities:
 * - View submitted applications
 * - Verify applications after document checking
 * - Reject applications if documents are invalid
 *
 * This service acts as an intermediate layer between
 * Controller and Repository.
 */
@Service
public class ClerkService {

    /**
     * Repository used to perform CRUD operations
     * on Application entity.
     */
    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private StatusHistoryService historyService;
    
    @Autowired
    private ClerkRepository clerkRepository;

    /**
     * Fetch all applications with status = SUBMITTED.
     *
     * These applications are pending verification
     * by the clerk and will be shown on Clerk Dashboard.
     *
     * @return list of submitted applications
     */
    public List<Application> getSubmittedApplications() {
        return applicationRepository.findByStatus("SUBMITTED");
    }

    /**
     * Fetch a specific application by its ID.
     *
     * Used when clerk wants to view full details
     * of an application before taking action.
     *
     * @param applicationId unique identifier of application
     * @return Application entity
     * @throws RuntimeException if application not found
     */
    public Application getApplicationById(int applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found with ID: " + applicationId));
    }

    /**
     * Verify an application after successful document verification.
     *
     * Business Flow:
     * - Clerk verifies documents
     * - Application status is updated to VERIFIED
     * - Application becomes available for Officer review
     *
     * @param applicationId application identifier
     */
    public void verifyApplication(int applicationId) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        String oldStatus = app.getStatus();   // SUBMITTED

        app.setStatus("VERIFIED");
        applicationRepository.save(app);

        // 🧾 Save history
        historyService.logStatusChange(
                app,
                oldStatus,
                "VERIFIED",
                "CLERK",
                "ClerkUser" // later replace with logged-in username
        );
    }


    /**
     * Reject an application if documents are invalid
     * or information is incorrect.
     *
     * Business Flow:
     * - Clerk rejects application
     * - Status is updated to REJECTED
     * - Application process stops here
     *
     * @param applicationId application identifier
     */
    public void rejectApplication(int applicationId, String reason, String clerkName) {

        // Fetch application
        Application application = getApplicationById(applicationId);

        // Update status
        application.setStatus("REJECTED_BY_CLERK");
        application.setRejectionReason(reason);
        application.setRejectedBy("Clerk: " + clerkName);

        // Save updated application
        applicationRepository.save(application);
        
       
    }
    
    
    public Clerk getClerkByEmail(String email) {
        return clerkRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Clerk not found with email: " + email));
    }

}

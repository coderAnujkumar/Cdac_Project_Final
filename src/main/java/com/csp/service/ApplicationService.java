package com.csp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Citizen;
import com.csp.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public Application submitApplication(Citizen citizen,
                                         String serviceType,
                                         String purpose,
                                         String address) {

        // 🔴 CHECK LAST APPROVED APPLICATION
        Optional<Application> lastApproved =
                applicationRepository
                        .findTopByCitizen_CitizenIdAndServiceTypeAndStatusOrderByApplicationDateDesc(
                                citizen.getCitizenId(),
                                serviceType,
                                "APPROVED");

        if (lastApproved.isPresent()) {

            LocalDate lastDate = lastApproved.get().getApplicationDate();
            long days = ChronoUnit.DAYS.between(lastDate, LocalDateTime.now());

            if (days < 30) {
                throw new RuntimeException(
                    "You already received this certificate. Reapply after " +
                    (30 - days) + " days."
                );
            }
        }

        // 🟢 CREATE NEW APPLICATION
        Application app = new Application();
        app.setCitizen(citizen);
        app.setServiceType(serviceType);
        app.setPurpose(purpose);
        app.setAddress(address);
        app.setStatus("SUBMITTED");
        app.setApplicationDate(LocalDate.now());

        return applicationRepository.save(app);
    }

    public List<Application> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(status);
    }

    // ✅ NEW: FILTER PENDING BY SERVICE
    public List<Application> getSubmittedByService(String serviceType) {
        return applicationRepository.findByStatusAndServiceType("SUBMITTED", serviceType);
    }

    public Application getApplicationById(int id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public List<Application> getSubmittedApplications() {
        return applicationRepository.findByStatus("SUBMITTED");
    }

    public void verifyApplicationByClerk(int applicationId) {
        Application application = getApplicationById(applicationId);
        application.setStatus("VERIFIED");
        applicationRepository.save(application);
    }

    public void rejectByClerk(int applicationId) {
        Application application = getApplicationById(applicationId);
        application.setStatus("REJECTED");
        applicationRepository.save(application);
    }

    public List<Application> getVerifiedApplications() {
        return applicationRepository.findByStatus("VERIFIED");
    }

    public List<Application> getApplicationsByCitizen(int citizenId) {
        return applicationRepository.findByCitizenCitizenId(citizenId);
    }

    public List<Application> getRejectedApplications() {
        return applicationRepository.findByStatus("REJECTED_BY_CLERK");
    }
}

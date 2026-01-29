package com.csp.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Citizen;
import com.csp.entity.Clerk;
import com.csp.entity.Officer;
import com.csp.entity.Status;
import com.csp.entity.User;
import com.csp.repository.*;

@Service
public class AdminService {

    private final ClerkRepository clerkRepository;
    private final OfficerRepository officerRepository;
    private final StatusRepository statusRepository;
    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final CitizenRepository citizenRepository;
    private final UserRepository userRepository;



    public AdminService(
            ClerkRepository clerkRepository,
            OfficerRepository officerRepository,
            StatusRepository statusRepository,
            ApplicationRepository applicationRepository,
            PasswordEncoder passwordEncoder,
            CitizenRepository citizenRepository,
            UserRepository userRepository) {

        this.clerkRepository = clerkRepository;
        this.officerRepository = officerRepository;
        this.statusRepository = statusRepository;
        this.applicationRepository = applicationRepository;
        this.passwordEncoder = passwordEncoder;
        this.citizenRepository = citizenRepository;
        this.userRepository=userRepository;

    }

    // ================= USER CREATION =================

    public void createClerk(Clerk clerk) {
        Status active = statusRepository.findByStatusName("ACTIVE");

        String encodedPassword = passwordEncoder.encode(clerk.getPassword());

        // 🔐 AUTH TABLE ENTRY
        User user = new User();
        user.setUsername(clerk.getEmail());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_CLERK");
        user.setEnabled(true);
        userRepository.save(user);

        // 👤 PROFILE TABLE ENTRY
        clerk.setPassword(encodedPassword);
        clerk.setRole("ROLE_CLERK");
        clerk.setStatus(active);
        clerk.setEnabled(true);
        clerkRepository.save(clerk);
    }


    public void createOfficer(Officer officer) {
        Status active = statusRepository.findByStatusName("ACTIVE");

        String encodedPassword = passwordEncoder.encode(officer.getPassword());

        // 🔐 AUTH ENTRY
        User user = new User();
        user.setUsername(officer.getEmail());
        user.setPassword(encodedPassword);
        user.setRole("ROLE_OFFICER");
        user.setEnabled(true);
        userRepository.save(user);

        // 👤 PROFILE ENTRY
        officer.setPassword(encodedPassword);
        officer.setRole("ROLE_OFFICER");
        officer.setStatus(active);
        officer.setEnabled(true);
        officerRepository.save(officer);
    }


    // ================= DASHBOARD STATS =================

    public long countAllApplications() {
        return applicationRepository.count();
    }

    public long countByStatus(String status) {
        return applicationRepository.countByStatus(status);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // ================= USER MANAGEMENT =================

    public List<Clerk> getAllClerks() {
        return clerkRepository.findAll();
    }

    public List<Officer> getAllOfficers() {
        return officerRepository.findAll();
    }
    
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }


    // Toggle Clerk Active/Inactive
    public void toggleClerkStatus(int id) {
        Clerk clerk = clerkRepository.findById(id).orElseThrow();
        clerk.setEnabled(!clerk.isEnabled());
        clerkRepository.save(clerk);
        
        User user = userRepository.findByUsername(clerk.getEmail()).orElseThrow();
        user.setEnabled(clerk.isEnabled());
        userRepository.save(user);
    }

    // Toggle Officer Active/Inactive
    public void toggleOfficerStatus(int id) {
        Officer officer = officerRepository.findById(id).orElseThrow();
        officer.setEnabled(!officer.isEnabled());
        officerRepository.save(officer);
        User user = userRepository.findByUsername(officer.getEmail()).orElseThrow();
        user.setEnabled(officer.isEnabled());
        userRepository.save(user);
    }
}

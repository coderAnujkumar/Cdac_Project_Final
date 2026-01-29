package com.csp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Citizen;
import com.csp.entity.Status;
import com.csp.repository.ApplicationRepository;
import com.csp.repository.CitizenRepository;

@Service // this as business logic class.
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository; 

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= REGISTER =================
    //Saves citizen in DB
    public Citizen registerCitizen(Citizen citizen, Status status) { 
        citizen.setStatus(status);
        return citizenRepository.save(citizen);
    }

    // ================= GET BY EMAIL =================
    public Citizen getCitizenByEmail(String email) {
        return citizenRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Citizen not found with email: " + email));
    }

    // ================= GET BY ID =================
    public Citizen getCitizenById(int id) {
        return citizenRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Citizen not found with id: " + id));
    }
    // ✅ UPDATE NAME + PHOTO
    public void updateCitizenProfile(String email, String name, String photoFileName) {

        Citizen citizen = getCitizenByEmail(email);

        citizen.setName(name);

        if (photoFileName != null) {
            citizen.setProfilePhoto(photoFileName);
        }

        citizenRepository.save(citizen);
    }

    // ================= UPDATE PROFILE (NAME + PHOTO) =================
    public void updateCitizenProfile(String email, Citizen updated) {
        Citizen citizen = getCitizenByEmail(email);
        citizen.setName(updated.getName());

        // ✅ update photo if provided
        if (updated.getProfilePhoto() != null) {
            citizen.setProfilePhoto(updated.getProfilePhoto());
        }

        citizenRepository.save(citizen);
    }

    // ================= CHANGE PASSWORD =================
    public boolean changePassword(String email, String oldPwd, String newPwd) {

        Citizen citizen = getCitizenByEmail(email);

        if (!passwordEncoder.matches(oldPwd, citizen.getPassword()))
            return false;

        citizen.setPassword(passwordEncoder.encode(newPwd));
        citizenRepository.save(citizen);
        return true;
    }

    // ================= SAVE (FOR PROFILE PHOTO ETC) =================
    public Citizen save(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    // ================= SUBMIT APPLICATION =================
    public Application submitApplication(
            Citizen citizen,
            String serviceType,
            String purpose,
            String address) {

        Application application = new Application();

        application.setCitizen(citizen);
        application.setServiceType(serviceType);
        application.setPurpose(purpose);
        application.setAddress(address);
        application.setApplicationDate(LocalDate.now());
        application.setStatus("SUBMITTED");

        return applicationRepository.save(application); //Saved in DB  .save come from CrudRepository interface 
    }

    // ================= VIEW APPLICATIONS =================
    public List<Application> viewApplications(int citizenId) {
        return applicationRepository.findByCitizenCitizenId(citizenId);
    }
}

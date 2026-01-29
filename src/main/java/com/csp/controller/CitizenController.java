package com.csp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csp.entity.Application;
import com.csp.entity.Citizen;
import com.csp.repository.ApplicationStatusHistoryRepository;
import com.csp.service.ApplicationService;
import com.csp.service.CitizenService;
import com.csp.service.DocumentService;

@Controller
@RequestMapping("/citizen")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ApplicationStatusHistoryRepository historyRepo;

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        String email = principal.getName();

        Citizen citizen = citizenService.getCitizenByEmail(email);

        List<Application> applications =
                applicationService.getApplicationsByCitizen(citizen.getCitizenId());

        model.addAttribute("citizen", citizen);
        model.addAttribute("applications", applications);

        return "citizen-dashboard";
    }

    // ================= APPLY PAGE =================
    @GetMapping("/apply")
    public String applyPage() {
        return "citizen-apply";
    }

    // ================= SUBMIT APPLICATION =================
    @PostMapping(value = "/apply", consumes = "multipart/form-data")
    public String applyService(@RequestParam String serviceType,
                               @RequestParam String purpose,
                               @RequestParam String address,
                               @RequestParam String documentType,
                               @RequestParam MultipartFile file,
                               Principal principal,
                               RedirectAttributes ra) {

        String email = principal.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);

        try {
            Application application =
                    applicationService.submitApplication(citizen, serviceType, purpose, address);

            documentService.uploadDocument(
                    application.getApplicationId(),
                    documentType,
                    file
            );

            ra.addFlashAttribute("success", "Application submitted successfully!");

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/citizen/dashboard";
    }

    // ================= VIEW APPLICATIONS =================
    @GetMapping("/applications")
    public String viewApplications(Model model, Principal principal) {

        String email = principal.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);

        List<Application> applications =
                applicationService.getApplicationsByCitizen(citizen.getCitizenId());

        model.addAttribute("applications", applications);

        return "citizen-applications";
    }

    // ================= APPLICATION DETAILS =================
    @GetMapping("/application/{id}")
    public String viewApplicationDetails(@PathVariable int id,
                                         Model model,
                                         Principal principal) {

        String email = principal.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);

        Application app = applicationService.getApplicationById(id);

        if (app.getCitizen().getCitizenId() != citizen.getCitizenId()) {
            throw new RuntimeException("Unauthorized access");
        }

        model.addAttribute("history",
                historyRepo.findByApplication_ApplicationIdOrderByChangedAtDesc(id));

        model.addAttribute("app", app);

        return "citizen-application-details";
    }

}

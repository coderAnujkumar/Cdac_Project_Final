package com.csp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.csp.entity.Application;
import com.csp.entity.Clerk;
import com.csp.entity.Document;
import com.csp.service.ApplicationService;
import com.csp.service.ClerkService;
import com.csp.service.DocumentService;

@Controller
@RequestMapping("/clerk")
public class ClerkController {

    @Autowired
    private ClerkService clerkService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/dashboard")
    public String clerkDashboard(
            @RequestParam(required = false) String serviceType,
            Model model,
            Principal principal) {

        String email = principal.getName();
        Clerk clerk = clerkService.getClerkByEmail(email);

        model.addAttribute("clerkName", clerk.getName());

        List<Application> pendingApps;

        if (serviceType == null || serviceType.equals("ALL")) {
            pendingApps = applicationService.getApplicationsByStatus("SUBMITTED");
            model.addAttribute("selectedService", "ALL");
        } else {
            pendingApps = applicationService.getSubmittedByService(serviceType);
            model.addAttribute("selectedService", serviceType);
        }

        model.addAttribute("pendingApps", pendingApps);

        model.addAttribute("verifiedApps",
                applicationService.getApplicationsByStatus("VERIFIED"));

        model.addAttribute("rejectedApps",
                applicationService.getRejectedApplications());

        return "clerk-dashboard";
    }

    @GetMapping("/application/{id}")
    public String viewApplication(@PathVariable int id, Model model) {

        Application app = clerkService.getApplicationById(id);
        List<Document> documents = documentService.getDocumentsByApplicationId(id);

        model.addAttribute("app", app);
        model.addAttribute("documents", documents);

        return "clerk-application-details";
    }

    @PostMapping("/verify/{id}")
    public String verifyApplication(@PathVariable int id) {
        clerkService.verifyApplication(id);
        return "redirect:/clerk/dashboard";
    }

    @PostMapping("/reject/{id}")
    public String rejectApplication(
            @PathVariable int id,
            @RequestParam String reason,
            Principal principal) {

        String clerkEmail = principal.getName();
        Clerk clerk = clerkService.getClerkByEmail(clerkEmail);

        clerkService.rejectApplication(id, reason, clerk.getName());

        return "redirect:/clerk/dashboard";
    }
}

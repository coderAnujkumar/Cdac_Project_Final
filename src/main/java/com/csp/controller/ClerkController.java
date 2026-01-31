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
            Model model, //send data to UI
            Principal principal) { //logged-in clerk info

        String email = principal.getName();
        Clerk clerk = clerkService.getClerkByEmail(email);

        model.addAttribute("clerkName", clerk.getName());

        List<Application> pendingApps;

//        Shows all submitted applications
        if (serviceType == null || serviceType.equals("ALL")) {
            pendingApps = applicationService.getApplicationsByStatus("SUBMITTED");
            model.addAttribute("selectedService", "ALL");
        } else {
            pendingApps = applicationService.getSubmittedByService(serviceType);
            model.addAttribute("selectedService", serviceType);
        }

        model.addAttribute("pendingApps", pendingApps);

        model.addAttribute("verifiedApps",
                applicationService.getClerkVerifiedApplications());

        model.addAttribute("rejectedApps",
                applicationService.getRejectedApplications());

        return "clerk-dashboard";
    }

    @GetMapping("/application/{id}")
    public String viewApplication(@PathVariable int id, Model model) {

    	//fetch Application
        Application app = clerkService.getApplicationById(id);
        List<Document> documents = documentService.getDocumentsByApplicationId(id); //fatch documents

        model.addAttribute("app", app);
        model.addAttribute("documents", documents);

        return "clerk-application-details";
    }

    @PostMapping("/verify/{id}")
    public String verifyApplication(@PathVariable int id, Principal principal) {
    	//Status → VERIFIED Verified by → clerk name
        String clerkName = clerkService.getClerkByEmail(principal.getName()).getName(); //fatch name 
        clerkService.verifyApplication(id, clerkName);
        return "redirect:/clerk/dashboard";
    }

    @PostMapping("/reject/{id}")
    public String rejectApplication(
            @PathVariable int id,
            @RequestParam String reason,
            Principal principal) {

        String clerkName = clerkService.getClerkByEmail(principal.getName()).getName();
        clerkService.rejectApplication(id, reason, clerkName);

        return "redirect:/clerk/dashboard";
    }
}
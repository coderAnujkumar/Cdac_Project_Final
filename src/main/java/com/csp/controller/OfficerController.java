package com.csp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.csp.entity.Application;
import com.csp.entity.Officer;
import com.csp.service.ApplicationService;
import com.csp.service.OfficerService;

/**
 * OfficerController
 * ------------------
 * Handles officer-related web requests such as:
 * - Viewing verified applications
 * - Approving or rejecting applications
 */
@Controller
@RequestMapping("/officer")
public class OfficerController {

    @Autowired
    private OfficerService officerService;
    
    @Autowired
    private ApplicationService applicationService;

    /**
     * Officer Dashboard
     * ------------------
     * Displays all VERIFIED applications.
     */
    @GetMapping("/dashboard")
    public String officerDashboard(Model model, Principal principal) {

        String email = principal.getName();
        Officer officer = officerService.getOfficerByEmail(email);

        model.addAttribute("officerName", officer.getName());

        // 🔹 Pending (Verified by Clerk)
        List<Application> pendingApps =
                applicationService.getApplicationsByStatus("VERIFIED");

        // 🔹 Approved by Officer
        List<Application> approvedApps =
                applicationService.getApplicationsByStatus("APPROVED");

        // 🔹 Rejected by Officer
        List<Application> rejectedApps =
                applicationService.getApplicationsByStatus("REJECTED_BY_OFFICER");

        model.addAttribute("pendingApps", pendingApps);
        model.addAttribute("approvedApps", approvedApps);
        model.addAttribute("rejectedApps", rejectedApps);

        return "officer-dashboard";
    }

    
    // ✅ VIEW APPLICATION DETAILS (ADD THIS)
    @GetMapping("/review/{id}")
    public String viewApplication(@PathVariable int id, Model model) {

        Application app = officerService.getApplicationById(id);

        model.addAttribute("app", app);
        return "officer-application-details"; // HTML file name
    }

    /**
     * Approve application.
     */
    @PostMapping("/approve/{id}")
    public String approve(@PathVariable int id) {
        officerService.approveApplication(id);
        return "redirect:/officer/dashboard";
    }

    /**
     * Reject application.
     */
    @PostMapping("/reject/{id}")
    public String reject(@PathVariable int id,
                         @RequestParam String reason,
                         Principal principal) {

        Officer officer = officerService.getOfficerByEmail(principal.getName());
        officerService.rejectApplication(id, reason, officer.getName());

        return "redirect:/officer/dashboard";
    }

}

package com.csp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.csp.entity.Clerk;
import com.csp.entity.Officer;
import com.csp.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ================= DASHBOARD =================

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalApps", adminService.countAllApplications());
        model.addAttribute("approvedApps", adminService.countByStatus("APPROVED"));
        model.addAttribute("rejectedApps", adminService.countByStatus("REJECTED"));
        return "admin-dashboard";
    }

    // ================= APPLICATION MONITORING =================

    @GetMapping("/applications")
    public String viewAllApplications(Model model) {
        model.addAttribute("applications", adminService.getAllApplications());
        return "admin-applications";
    }

    // ================= USER CREATION =================

    @GetMapping("/create-user")
    public String createUserPage(Model model) {
        model.addAttribute("clerk", new Clerk());
        model.addAttribute("officer", new Officer());
        return "admin-create-user";
    }

    @PostMapping("/create-clerk")
    public String createClerk(@ModelAttribute Clerk clerk) {
        adminService.createClerk(clerk);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/create-officer")
    public String createOfficer(@ModelAttribute Officer officer) {
        adminService.createOfficer(officer);
        return "redirect:/admin/dashboard";
    }

    // ================= USER MANAGEMENT =================
    
    
    @GetMapping("/citizens")
    public String viewCitizens(Model model) {
        model.addAttribute("citizens", adminService.getAllCitizens());
        return "admin-citizens";
    }


    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("clerks", adminService.getAllClerks());
        model.addAttribute("officers", adminService.getAllOfficers());
        return "admin-manage-users";
    }

    @GetMapping("/clerk/toggle/{id}")
    public String toggleClerk(@PathVariable int id) {
        adminService.toggleClerkStatus(id);
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/officer/toggle/{id}")
    public String toggleOfficer(@PathVariable int id) {
        adminService.toggleOfficerStatus(id);
        return "redirect:/admin/manage-users";
    }
}
